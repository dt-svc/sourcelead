<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!-- <script src="/resources/js/summernote.js"></script> -->

<%-- <link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css"> --%>

<style>
    .modal-backdrop{ display: none;    }
    .nicEdit-main { width: 100% !important; overflow: auto !important; max-height: 220px !important; min-height: 220px !important; padding: 10px; margin: 0px !important;}
    .mt40 { margin-top: 50px !important; }
    .compose-mail .chosen-container-multi .chosen-choices li.search-field input[type="text"] {
        height: 30px;
        line-height: 35px;
        width: 500px !important;
    }
    .txt-area > div { width: 100% !important; max-height: 220px !important;}
    .chosen-container-multi .chosen-choices { margin-bottom: 0px; }
    .nicEdit-pane { z-index: 9999999; top: 100px;}
</style>

<!-- <script>
 window.onload = function() {
	 getMails('Inbox');
};
</script> -->	

<div id="loading"></div>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
       <div id="loading"></div>
        <h2>Mailbox
			<a href="javascript:void(0)" onclick="getEmialSettings();"><i class="fa fa-cog ml"></i></a>        
        </h2>
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
		   

		<div class="mail-box" id="loading">
		

		      <div class="mailBtn">
					<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${mailType == 'mails'}">
						<c:choose>
							<c:when test="${emailAccountSettingsServiceList.size() > 0}">
								<button id="composeDynamicMail" class="newAdBtn" 
									type="button" onClick="showComposeEmailPopup();">
									<i class="fa fa-envelope-o" aria-hidden="true"></i>Compose
								</button>
							</c:when>
							<c:otherwise>
								<button id="composeSourceleadMail" class="newAdBtn" type="button"
									onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/composeMail'/>'">
									<i class="fa fa-envelope-o" aria-hidden="true"></i>Compose
								</button>
							</c:otherwise>
						</c:choose>
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
		
			<ul class="userMailList">
				<c:choose>
					<c:when test="${emailAccountSettingsServiceList.size() > 0}">
						<c:forEach var="emailAccountList" items="${emailAccountSettingsServiceList}">
							<li>
								<span class="color${emailAccountList.email.split("@")[1].charAt(0)}" onclick="activeEmail(${emailAccountList.id}, this);">${emailAccountList.email.split("@")[1].charAt(0)}</span>
								<p onclick="activeEmail(${emailAccountList.id}, this);">${emailAccountList.email}</p>
								<div class="mActions">
									<i class="mailIcon trashIcon" onclick="deleteConMail1(${emailAccountList.id});"></i>
									<i class="mailIcon editIcon" onclick="getConfiguredEmail(${emailAccountList.id});"></i>
								</div>								
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li class="Act">
							<span class="colors">S</span>
							<p onclick="activeEmail(0, this);">${SourceLeadWeb_user.email}</p>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
        </div>

          <div class="mail-box" id="sourceleadEmailFolderDiv" style="display:block">
              <h5>Folders</h5>
            <div class="mailSrl">
              <ul class="nav nav-pills nav-stacked">
                <li id="inboxActive" class=""><a href="#inbox" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox <c:if test="${inboxUnreadMailsCount > 0}"><span class="label label-primary pull-right" id="inboxUnread">${inboxUnreadMailsCount}</span></c:if>
                <li id="sentActive" class=""><a href="#sent" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li id="starredActive" class=""><a href="#starred" onclick="getMails('Starred');"><i class="fa fa-star"></i> Starred</a></li>
                <c:if test="${mailType == 'hotcampaigns'}">
                <li id="requestedActive" class=""><a href="#requested" onclick="getMails('Requested');"><i class="fa fa-file-text-o"></i> Requested <c:if test="${requestedUnreadMailsCount > 0}"><span class="label label-info pull-right" id="requestedUnread">${requestedUnreadMailsCount}</span></c:if></a></li> 
                </c:if>
                <li id="trashActive" class=""><a href="#trash" onclick="getMails('Trash');"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
        <div class="mail-box" id="dynamicEmailFolderDiv" style="display: none;">
 			<h5>Folders</h5>
			<div class="mailSrl">
				<div id="dynamicEmailFolderList"></div>
				<div id="dynamicEmailFolderLoading" class="mailLoad"></div>
			</div>
		</div>



        </div>
		<div class="col-md-9" id="inbox">
			<div class="dashNewWrap mail-box mail-content dashNewWrapMinHe">
				<div id="mailLoading"></div>
				<div class="mail-box-header clearfix">
					<div class="pull-left mr" id="sourceleadMailCheckAll">
						<input id="checkAllEmails" class="checkbox-custom" onclick="toggleEmails(this);deleteMail();"
							name="checkAllEmails" type="checkbox">
						<label for="checkAllEmails" id="checkAllBtn"
							class="checkbox-custom-label" style="display: none;"></label>
					</div>
					<div class="pull-left mr" id="dynamicMailCheckAll" style="display: none;">
						<input type="checkbox" id="a" class="checkbox-custom" onclick="selectOrDeselectAllEmails(this)">
						<label for="a" class="checkbox-custom-label"></label>
					</div>
					<h3 class="box-title pull-left mr" id="mailboxLabel"></h3>
					
					<div class="pull-left" id="SourceleadMailControlsMaster">
						<span class="pull-left mr" id="refreshBtn" style="display: none;" onclick="refreshList();">
							<i class="mailIcon refreshIcon"></i>
						</span>						
						<span class="pull-left" onclick="getMailBoxIds();" id="trashBtn" style="display: none;">
							<i class="mailIcon trashIcon" id="trashtype"></i>
						</span>
					</div>

					<div class="pull-left mr5" title="Refresh"><i class="mailIcon refreshIcon" style="display: none;"></i></div>
	
	
	
					<div class="pull-left" id="DynamicMailControlsMaster" style="display: none;">
						<span class="pull-left mr moveToTrash" style="display: none;">
							<i class="mailIcon trashIcon" onclick="moveEmailsToTrash(false);"></i>
						</span>
						<span class="pull-left deleTrash deleteForever" onclick="moveEmailsToTrash(true);" style="display: none;">Delete Forever</span>
					</div>




						</div>
				<div class="mailbox-messages mailbox-table">
					<div id="mailBoxList"></div>
				</div>
				<!-- mail box table ends -->
			</div>
		</div>

		<div class="col-md-9" id="details" style="display: none;">
			<div class="dashNewWrap mail-box mail-content dashNewWrapMinHe">
				<div id="body-details" style="display: none;"></div>
				<div id="mailContentLoading"></div>
			</div>
		</div>
		
				<!-- Mail view area -->
			</div>
		   </div>
		</div>
  </section>
  
  <div class="newPopOut newPopOutTop" id="configureEmailDiv" style="display: none;">
	<div class="newPopWrap">
  
  			<div class="popupHead">
				<span>Please select your provider</span>
				<i class="closePop pull-right popClose" onclick="$('#configureEmailDiv').hide();"></i>
			</div>
  
				<div class="newMailCon">
					<ul>
						<li>
							<a onclick="getEmailSettings('gmail');">
							<img src="/resources/images/gmail-logo.jpg" alt="" />Gmail
							</a>
						</li>
						<li>
							<a onclick="getEmailSettings('yahoo');">
							<img src="/resources/images/yahoo-logo.jpg" alt="" />Yahoo
							</a>
						</li>
						<li>
							<a onclick="getEmailSettings('others');">
							<img src="/resources/images/mail-icon.jpg" alt="" />Other
							</a>
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			<div class="popupFoot text-right">
				<button type="button" class="slBtn md popClose" onclick="$('#configureEmailDiv').hide();">Cancel</button>
			</div>
		</div>
		</div>
  

<!-- Dynamic Mail Compose popup-->
<div class="side_mask"></div>
<div id="emailConfigurationPopup"></div>

<div class="newPopOut" id="dynamicmailcompose" style="display: none;">
	<div id="composeLoading"></div>
    <div class="newPopWrap sentMailPop">
        <div class="popupHead">
            <span>Compose Mail</span>
            <i class="closePop pull-right popClose" onclick="closeComposeEmailPopup();"></i>
        </div>
        <div class="sentMailWrap clearfix">
                <form:form id="email_form" name="email_form">
                
                    <div class="sentMailCta">
                        <label>From</label>
                        <select id="fromEmail" name="from" data-placeholder="Choose an Email to send ...." class="chosen-select form-control">
                            <option value=""></option>
                            <c:forEach var="emailAccountList" items="${emailAccountSettingsServiceList}">
                                <option data-id="${emailAccountList.id}" value="${emailAccountList.email}">${emailAccountList.email}</option>
                            </c:forEach>
                        </select>                        
                    </div>
                    
                    <div class="sentMailCta sentMailCtaCC">
                        <label>To</label>
                        <select class="form-control chosen-select" field-name="To List" multiple
                            data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email"
                            id="emailsList" name="to" data-validation-optional="true"
                            data-validation="validate_email" field-name="Toemails">
                        </select>
                        <a class="mailCC" href="javascript:void(0)" onclick="$('#sentMailCC').slideToggle(0);">CC</a>                    
                    </div>
                    
                    <div class="sentMailCta sentMailCtaCC" style="display: none;" id="sentMailCC">
                        <label>CC</label>
                        <select class="form-control chosen-select" field-name="Cc List" multiple
                            data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email"
                            id="ccList" name="cc" data-validation-optional="true"
                            data-validation="validate_email" field-name="Toemails">
                        </select>                    
                    </div>
                    
                    <div class="sentMailCta">
                        <label>Subject</label>
                        <input type="text" name="subject" data-validation="required" field-name="Subject" id="sendSubject" value="" class="mailSubfild">                    
                    </div>
                    
                        <div class="sentAttach">
                            <input name="attachfile" id="attachfileBtn" class="sentAttachFild" type="file" multiple="multiple">
                            <a class="attBtn">Attach File <i class="fa fa-paperclip"></i> </a>
                            <!-- Attachments preview-->
                            <div class="mailAttachments clearfix" id="mailAttachmentsDiv"></div>
                            <!-- Attachments preview-->                                
                        </div>
                    
                    
                    <div class="clearfix">
                        <div class="clearfix txt-area">
                          <textarea id="bodytext" name="body" class="form-control" rows="5" placeholder="Message" style="height: 170px; max-height: 170px !important; width:100%;"></textarea>
                        </div>
                    </div>    
                        
                        <!-- Attachment button -->
                    </form:form>
        </div>
        <div class="popupFoot">
            <input value="Send" id="sendEmail" onclick="sendEmailFromMailBox('compose');" class="slBtn md colorBtn" type="button">
            <input value="Reply" id="replyEmail" onclick="replyEmailFromMailBox('reply');" class="slBtn md colorBtn" type="button" style="display: none;">
            <input value="Forward" id="forwardEmail" onclick="sendEmailFromMailBox('forward');" class="slBtn md colorBtn" type="button" style="display: none;">
            <input value="Cancel" id="clear_search" onclick="closeComposeEmailPopup();" class="slBtn md popClose" type="button">
        </div>            
            
    </div>
</div>

<div class="newSusMes" id='textBoxmailerPopup'>
	<p><span id='emailResponseText'></span><i class="fa fa-close" onclick='textBoxCancel();'></i></p>
</div>

<script src="/resources/js/nicEdit-latest.js"></script>
<script src="/resources/js/nicEdit.js"></script>

<script type="text/javascript">
	var mailType = '${mailType}';
	$(function() { 
		tagIt("emailsList", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		tagIt("ccList", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		
		$("#attachfileBtn").change(function() {
			uploadAttachment("attachfileBtn", "Email Attachments", "email_form");
			$(".clearUpload").show();
		});
	});

  function getMails(type) {
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  
	  $("#inbox").show();
	  $("#details").hide();
	  $("#configureEmailDiv").hide();
	  $("#trashBtn").hide();
	  
	  $('#checkAllEmails').removeAttr('checked');
	  $('#mailboxLabel').html(type);
	  
	  if(type == "Trash") {
		  $("#trashtype").removeClass("trashIcon mailIcon");
		  $("#trashtype").addClass("deleTrash deleteForever");
		  $('#trashtype').html('<b>Delete For Ever</b>');
	  } else {
		  $("#trashtype").addClass("trashIcon mailIcon");
		  $("#trashtype").removeClass("deleTrash deleteForever");
		  $('#trashtype').html('');
	  }
	  
	  setActiveTab(type)
	  
	  
	  /* $("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;'); */
	  $("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMailsFromELK?${_csrf.parameterName}=${_csrf.token}&type="+type+"&mailType="+mailType,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		
                	 /* document.getElementById("checkAllBtn").style.display="";
                	 document.getElementById("refreshBtn").style.display=""; */
                	 $("#sourceleadMailCheckAll").show();
                	 $("#checkAllBtn").show();
                	 $("#refreshBtn").show();
                	 
              			var result = '';
              			result = result +'<input type="hidden" id="emailListType" value='+type+'>';
              			result = result +'<table id="mails" class="table table-hover table-striped ">';
              			
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
		              			result = result +'<td style="width : 25px;">';
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
	                 	 /* $("#mailDetails").empty(); */
	                 	 $("#body-details").empty();
	                 	 $("#body-details").hide();
	                     $("#mailBoxList").append(result); 
	                     pagination(); 
	                     $("#mailLoading").removeAttr("style"); 
                }else{
                	$("#checkAllBtn").hide();
               	 	$("#refreshBtn").hide();
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
					/* $("#mailDetails").empty(); */ 
					$("#body-details").empty();
					$("#body-details").hide();
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
/* 		  if(mailType == 'mails'){
		  	document.getElementById("settingsActive").removeAttribute("class")
		   } */
		  document.getElementById('inboxActive').className = 'active'
	}
	
	else if(type == 'Sent'){
		  document.getElementById("sentActive").className = 'active'
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class") 
		  }
		  document.getElementById("trashActive").removeAttribute("class")
/* 		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  } */
		  
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Starred'){
		  document.getElementById("starredActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")  
		  }
		  document.getElementById("trashActive").removeAttribute("class")
/* 		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  } */
		  
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
/* 		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  } */
		   
	}
	
	else if(type == 'Trash'){
		  document.getElementById("trashActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById('inboxActive').removeAttribute("class")
/* 		   if(mailType == 'mails'){
			   document.getElementById("settingsActive").removeAttribute("class")
		   }
		   */
	}
/* 	else if(type == 'Settings'){
		  document.getElementById("settingsActive").className = 'active'
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById('inboxActive').removeAttribute("class")
	} */
	 
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
	 
	
	 $("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
function getEmailDetails(uuid,type) {
	var sourceleadUrl = '${sourceleadUrl}';
	$("#details").show();
	$("#inbox").hide();
	 
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
                 
                 
                 result = result +'<div class="mailDetailsHeader clearfix">';
                 result = result +'<div class="mailBack" title="back" onclick="backToEmailList();"></div>';
                 
                 result = result +'<span class="pull-left">';
                 result = result +'<i class="mailIcon repIcon" title="Reply to sender" onclick="replayToMail(\''+uuid+'\');"></i>';
	   		   	 if(type == 'Sent' && opt.candidateId != null){
                 	result = result +'<i class="mailIcon forIcon" title="Forward selected message" onclick="forwardEmailToHotMail(\''+uuid+'\');"></i>';
	   		    	}else{
	                 result = result +'<i class="mailIcon forIcon" title="Forward selected message" onclick="forwardEmail(\''+uuid+'\');"></i>';
	   		    	}
	   				result = result +'<i class="mailIcon trashIcon" title="Move to Trash" onclick="deleteReadMail(\''+uuid+'\',\''+opt.nextMailUUID+'\',\''+type+'\');"></i>';
                 result = result +'</span>';
                 
                 
              	result = result +'<div class="btn-group pull-right mt5">';
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
                
                result = result +'<div class="mailSentDate mr">'+opt.calDays+'</div>';
                
                result = result +'</div>';  
                
                
                
			    result = result +'<div class="mailDetailsWrap">';
			    result = result +'<h4>'+opt.subject+'</h4>';
			    result = result +'<div class="mailCcFrom">';
			    result = result +'<p><span>From</span> '+opt.fromMail+'</p>';
			    result = result +'<p><span>To</span> '+opt.toMail+'</p>'; 
			    result = result +'</div>';
			    
			    

	   			  
	   			result = result +'<div class="mailContent">';
	   			
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
            
	   		result = result +'</div></div>';
	   		
            /* $("#mailBoxList").empty(); */ 
            /* $("#mailDetails").empty(); 
            $("#mailDetails").append(result); */
            $("#body-details").empty();
            $("#body-details").append(result);
            $("#body-details").show();
            $("#mailContentLoading").removeAttr("style"); 
             }
		},
	});  
}

</script>


<script>
function getEmialSettings() {
	setActiveTab('Settings');
	$("#configureEmailDiv").show();
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
function activeEmail(id, ele){
	$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	$(".compose-mail").hide();
	$("#sourceleadMailCheckAll").hide();
	$("#dynamicMailCheckAll").hide();
	$(".userMailList").find("li").removeClass("Act");
	$(ele).parent().addClass("Act");
	if(id === 0) {
		getMails('Inbox');
		/* $("#composeSourceleadMail").show();
		$("#composeDynamicMail").hide(); */
		$("#sourceleadEmailFolderDiv").show();
		$("#dynamicEmailFolderDiv").hide();
		$("#SourceleadMailControlsMaster").show();
		$("#DynamicMailControlsMaster").hide();
		
		$("#mailLoading").removeAttr("style");
	} else {
		/* $("#composeSourceleadMail").hide();
		$("#composeDynamicMail").show(); */
		$("#sourceleadEmailFolderDiv").hide();
		$("#dynamicEmailFolderDiv").show();
		$("#dynamicEmailFolderLoading").show();
		$("#dynamicEmailFolderList").empty();
		$("#SourceleadMailControlsMaster").hide();
		$("#DynamicMailControlsMaster").show();
		
		
		$("#activeClass"+id).removeClass("Active");
		$("#activeClass"+id).addClass("Active");
		$("#mailBoxList").empty();
		$("#mailBoxList").append("<p class='text-center text-muted'><i class='noMailImg'></i></p>");
		$("#body-details").empty();
		$("#body-details").hide();
	
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/emailAccountRead?${_csrf.parameterName}=${_csrf.token}&id="+id,
			success : function(response) {
				if(response && response.data != null && response.data.length > 0) {
				 var result = "";
				 for(var e in response.data) {
					 var report = response.data[e];
					 var margin = report.split("<<>>")[1].split(".").length > 1 ? (report.split("<<>>")[1].split(".").length - (report.split("<<>>")[0] === "INBOX" ? 1 : 2)) : 0;
					 result = result + "<ul class='nav nav-pills nav-stacked dynMailList' style='margin-left : " + (margin * 20) + "px;'>";
					 result = result + "<li name='mailFolder' data-index='" + e + "' " + (report.split("<<>>")[2] > 0 ? "class='unseen-text'" : "") + " onClick='readFolder(\"" + report.split("<<>>")[0] + "\",\"" + report.split("<<>>")[1] + "\"," + id + ", 0, 10, " + e + ");'>" + report.split("<<>>")[0] + (report.split("<<>>")[2] > 0 ? " (" + report.split("<<>>")[2].trim() + ")" : "") + "</li>";
					 /* result = result + "<li onClick='readFolder(\"" + report + "\"," + id + ");'>" + report + "</li>"; */
					 result = result + "</ul>";
				 }
				 $("#dynamicEmailFolderList").append(result);
				 autoSelectFirstEmailFolder();
				} else {
					/* alert("Configured credentials were wrong,Please configure with right credentials") */
					showEmailResponse(true, response.responseMessage);
					$("#mailLoading").removeAttr("style");
				}
				$("#dynamicEmailFolderLoading").hide();
				/* $("#mailLoading").removeAttr("style"); */
			},
		}); 
	}
}

function autoSelectFirstEmailFolder() {
	/* $("#dynamicEmailFolderList").children(0).find("li").click(); */
	/* console.log($("#dynamicEmailFolderList").children(0).find("li").html()); */
	if($("li[name='mailFolder']").length > 0)
		$("li[name='mailFolder']")[0].click();
}
</script>
<script type="text/javascript">
/* var emailMap = new Map(); */
var activeAccountId, activeFolder, activeFolderShortName;
function readFolder(name, fullName, id, pageNo, pageSize, index){
	$('#mailboxLabel').html(name);
	$("#mailBoxList").empty();
	$("#mailBoxList").append("<p class='text-center text-muted'><i class='noMailImg'></i></p>");
	/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	$("#body-details").empty();
	$("#body-details").hide();
	
	$("#dynamicEmailFolderList li").removeClass("active");
	$("#dynamicEmailFolderList li[data-index=" + index + "]").addClass("active");
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/emailFolderRead?${_csrf.parameterName}=${_csrf.token}&folderName="+fullName+"&id="+id+"&pageNo="+pageNo+"&pageSize="+pageSize,
		success : function(data) {
					if(data && data.response === "SUCCESS") {
						var result = '';
						result = result +'<div class="mailTabWrap">';
			  			result = result +'<table id="emailMailDetailsPagination" class="table table-hover table-striped">';
			    		result = result +'<tbody>';
			    		var emailList = data.data;
			    		if(emailList.length > 0) {
				    		for(var e in emailList) {
							 	result = result +' <tr id="checkbox' + emailList[e].messageId + '" data-id=' + emailList[e].messageId + ' ' + (emailList[e].seen ? "" : "class='unseen'") + '>';
							 	
				       			result = result +'<td style="width : 20px;">';
				       			result = result +'<div class="mrleft8">';
				       			result = result +'<input id="check' + emailList[e].messageId + '" value=" " class="checkbox-custom"  name="mailIds[]" type="checkbox" onclick="showOrHideMailControls();">';
				       			result = result +'<label for="check' + emailList[e].messageId + '" class="checkbox-custom-label"></label>';
				       			result = result +'</div>';
				       			result = result +'</td>';
				       			
				       			result = result +'<td style="text-align: left !important;" class="mailbox-subject" onclick="getEmailBodyForMailBox(' + id + ',' + emailList[e].messageId + ',\'' + fullName + '\',\'' + name + '\')" data-id="' + emailList[e].messageId + '">';
				       			result = result +'	<div class="mailFrom">' + (fullName.indexOf("Sent") !== -1 ? (emailList[e].to != null ? emailList[e].to.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "") : (emailList[e].from != null ? emailList[e].from.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "")) + '</div>';
				       			result = result +'</td>';
				       			
				       			result = result +'<td style="text-align: left !important;" class="mailbox-subject" onclick="getEmailBodyForMailBox(' + id + ',' + emailList[e].messageId + ',\'' + fullName + '\',\'' + name + '\')" data-id="' + emailList[e].messageId + '">';
				       			result = result +'	<div class="mailSub">' + (emailList[e].subject !== null && emailList[e].subject !=="" ? emailList[e].subject : "(no subject)") + '</div>';
				       			result = result +'</td>';			       			
				       			
				       			if(emailList[e].attachmentsCount > 0)
				       				result = result + '<td><i class="mailIcon attaIcon"></i></td>';
				       			else 
				       				result = result + '<td></td>';
				       				
				       			result = result +'<td class="mailbox-date" data-id="' + emailList[e].messageId + '">' + emailList[e].sentDate + '</td>';
				       			result = result + '<td class="mailOver"><div class="mailActions">';
				       			/* result = result + '	<i class="mailIcon repIcon" title="Reply" onclick="showReplyEmailPopup(' + id + ', ' + emailList[e].messageId + ', \'' + fullName + '\', false);"></i>';
				       			result = result + '	<i class="mailIcon repallIcon" title="Reply All" onclick="showReplyEmailPopup(' + id + ', ' + emailList[e].messageId + ', \'' + fullName + '\', true);"></i>';
				       			result = result + '	<i class="mailIcon forIcon" title="Forward" onclick="showForwardEmailPopup(' + id + ', ' + emailList[e].messageId + ', \'' + fullName + '\');"></i>'; */
				       			if($("#mailboxLabel").html().toLowerCase() === "trash")
				       				result = result + '	<i class="mailIcon trashIcon" title="Delete Forever" onclick="moveEmailToTrash(' + id + ', ' + emailList[e].messageId + ', \'' + fullName + '\', \'' + name + '\', true);"></i>';
				       				/* <span class="pull-left deleTrash deleteForever" onclick="deleteEmailsForEver();" style="display: none;">Delete Forever</span> */
				       			else
				       				result = result + '	<i class="mailIcon trashIcon" title="Delete" onclick="moveEmailToTrash(' + id + ', ' + emailList[e].messageId + ', \'' + fullName + '\', \'' + name + '\', false);"></i>';
				       			result = result + '</td>';
				       				
				       			result = result +'</tr>';
				       			
				       			/* emailMap.set(emailList[e].messageId, emailList[e]); */
				    		}
				    		
				    		result = result +'</tbody>';
				    		result = result +"</table>";
				    		result = result +'</div>';
				    		$("#mailBoxList").empty();
				    		/* $("#mailDetails").empty(); */
				    		$("#body-details").empty();
				    		$("#body-details").hide();
						    $("#mailBoxList").append(result); 
						    /* emailMailDetailsPagination(); */
						    prepareDynamicPagination(name, fullName, id, data.paginator, index);
						    $("#dynamicMailCheckAll").show();
						    /* $(".refreshIcon").show(); */
			    		}
					} else {
						$("#dynamicMailCheckAll").hide();
						showEmailResponse(true, data.resposeMessage);
		    			/* $(".refreshIcon").hide(); */		
					}
				    $("#mailLoading").removeAttr("style"); 
				    $("#inbox").show();
				    $("#details").hide();
				    activeAccountId = id;
				    activeFolder = fullName;
				    activeFolderShortName = name;
		},
	});
}
function prepareDynamicPagination(name, fullName, id, paginator, index) {
	var text = "<div class='clearfix'>";
	text += "<div class='dataTables_length pull-left' id='emailMailDetailsPagination_length'>";
/* 	text += "	<label>Show ";
	text += "		<select name='emailMailDetailsPagination_length' aria-controls='emailMailDetailsPagination' class=''>";
	text += "			<option value='10'>10</option>";
	text += "			<option value='15'>15</option>";
	text += "			<option value='20'>20</option>";
	text += "			<option value='-1'>All</option>";
	text += "		</select>";
	text += "	entries</label>"; */
	text += "</div>";

	var unreadCount = paginator.filterType;
	$("li[data-index='"+ index +"']").html(name + (unreadCount > 0 ? " (" + unreadCount + ")" : ""));
	var resultsFrom = (paginator.pageNo * paginator.pageSize) + 1;
	var resultsTo = (paginator.pageNo + 1) * paginator.pageSize > paginator.totalRows ? paginator.totalRows : (paginator.pageNo + 1) * paginator.pageSize;	
	text += "<div class='dataTables_info pull-left' id='emailMailDetailsPagination_info' role='status' aria-live='polite'>Showing " + resultsFrom + " to " + resultsTo + " of " + paginator.totalRows + " entries</div>";

	var prevFlag = resultsFrom !== 1;
	var nextFlag = resultsTo !== paginator.totalRows;
	
	text += "<div class='dataTables_paginate paging_simple_numbers pull-right' id='emailMailDetailsPagination_paginate' style='position: relative; top: 11px; right: 10px;'>";
	text += "	<a class='fa fa-angle-left paginate_button previous' title='Previous'";
	if(prevFlag)
		text += " onclick='readFolder(\"" + name + "\", \"" + fullName + "\", " + id + ", " + (paginator.pageNo - 1) + ", " + paginator.pageSize + ", " + index + ");'>";
	else
		text += " style='cursor: no-drop;' >";
		text += "	</a>";
	text += "	<a class='fa fa-angle-right paginate_button next' title='Next'";
	if(nextFlag)
		text += " onclick='readFolder(\"" + name + "\", \"" + fullName + "\", " + id + ", " + (paginator.pageNo + 1) + ", " + paginator.pageSize + ", " + index + ");'>";
	else
		text += " style='cursor: no-drop;' >";
	text += " </a>";
	text += "</div>";
	text += "</div>";
	$("#mailBoxList").append(text);
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
function backToEmailList(){
	$("#inbox").show();
    $("#details").hide();
    emailDetails = null;
}

</script>

<script type="text/javascript">
	var emailDetails = null;
	function getEmailBodyForMailBox(emailAccountId, uid, folderName, folderShortName){
	    /* $("#mailDetails").html($("#mailBody"+id).html()); */
	    $("#body-details").empty();
	    $("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	    $.ajax({
			type : "POST",
			url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid="+uid + "&folderName=" + folderName,
			success : function(data) {
				if(data && data != null) {
					emailDetails = data;
					
					var body ="<div class='mailDetailsHeader clearfix'>";
					body += "	<div class='mailBack' title='back' onclick='backToEmailList();'></div>";
					body += "	<span class='pull-left'>";
					body += "		<i aria-hidden='true' class='mailIcon repIcon' title='Reply to sender' onclick='showReplyEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", false);'></i>";
					body += "		<i aria-hidden='true' class='mailIcon repallIcon' title='Reply to all senders/recipients' onclick='showReplyEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", true);'></i>";
					body += "		<i aria-hidden='true' class='mailIcon forIcon' title='Forward selected message' onclick='showForwardEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\");'></i>";
					if($("#mailboxLabel").html().toLowerCase() === "trash") 
						body += "		<i title='delete' onclick='moveEmailToTrash(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", \"" + folderShortName + "\", true);'>Delete forever</i>";
					else
						body += "		<i aria-hidden='true' class='mailIcon trashIcon' title='Move to Trash' onclick='moveEmailToTrash(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", \"" + folderShortName + "\", false);'></i>";
					
					body += "	</span>";
					body += "<div class='mailSentDate'>" + data.sentDate + "</div>";
					body += "</div>";
					
					
					body+="<div class='mailDetailsWrap' id='mailBody" + data.messageId + "'>"; 
					body+="<h4>" + data.subject + "</h4>";
	
					body+="<div class='mailCcFrom'>";
					/* body+="<p class='clearfix mb10 col-md-8'><i class='text-muted fa fa-user paddingRight'></i>" + (folderName.indexOf("Sent") !== -1 ? data.to : data.from) + "</p>"; */
					body+="<p><span>From</span> " + (data.from !== null ? data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "") + "</p>";
					body+="<p><span>To</span> " + (data.to !== null ? data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "") + "</p>";
					/* body+="<p class='clearfix mb10 col-md-8'>" + (folderName.indexOf("Sent") !== -1 ? "To" : "From") +" : " + (folderName.indexOf("Sent") !== -1 ? data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") : data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;")) + "</p>";
					body+="<p class='clearfix mb10 col-md-8'>" + (folderName.indexOf("Sent") !== -1 ? "From" : "To") +" : " + (folderName.indexOf("Sent") !== -1 ? data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") : data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;")) + "</p>"; */						
					if(data.cc != null && data.cc !== "") {
						body+="<p><span>Cc</span> " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</p>";
					}
					body+="</div>";
					body+="<div class='mailContent'>" + data.body + "</div>";
					
					
					if(data.filesList != null && data.filesList.length > 0) {
						var text = "<div class='mailAttaWrap clearfix'>";
						text += "       <h2>Attachments</h2>";
	
						for(var e in data.filesList) {
							var attachment = data.filesList[e];
							
							text += "           <div class='AttaCta'>";
							var attachClass = "fa-file-o";
							
							if(attachment.fileExtension === ".jpg" || attachment.fileExtension === ".jpeg" || attachment.fileExtension === ".png") {
								attachClass = "fa-file-image-o";
							} else if(attachment.fileExtension === ".pdf") {
								attachClass = "fa-file-pdf-o";
							} else if(attachment.fileExtension === ".xls" || attachment.fileExtension === ".xlsx") {
								attachClass = "fa-file-excel-o";
							} else if(attachment.fileExtension === ".doc" || attachment.fileExtension === ".docx") {
								attachClass = "fa-file-word-o";
							} else if(attachment.fileExtension === ".txt") {
								attachClass = "fa-file-text-o";
							} else if(attachment.fileExtension === ".rar") {
								attachClass = "fa-file-archive-o";
							}
							
							text += "               <div class='AttaIcon'>";
							if(attachment.fileExtension === ".jpg" || attachment.fileExtension === ".jpeg" || attachment.fileExtension === ".png")
								text += "					<img src='" + (attachment.url + "/" + attachment.fileName) + "' alt='" + attachment.fileName + "'></img>";
							else
								text += "					<i class='fa " + attachClass + "' aria-hidden='true'></i>";
							text += "				</div>";
							text += "               <div class='AttaText'><i class='fa fa-paperclip' aria-hidden='true'></i>" + attachment.fileName + "</div>";
							if(attachment.attachmentNotFoundOrCorrupted) {
								text += "<div class='textError'>File Contains error/virus</div>";								
							} else {
								text += "               <div class='AttaCtaAct' title='" + attachment.fileName + "'>";
								text += "                   <i class='fa fa-paperclip' aria-hidden='true'></i>";
								text += "                   <p>" + attachment.fileName + "</p>";
								text += "                   <a href='/app/downloadFile.html?file=" + (attachment.path + "/" + attachment.fileName) + "'><span class='fa fa-arrow-down' aria-hidden='true'></span></a>";
								text += "               </div>";
							}
							text += "           </div>";
							
							/* body += "<div class='col-md-10' style='width: auto;border: 1px solid #CECECE;'>";
							body += "<img src='" + attachment.url + "/" + attachment.fileName + "' style='width: 100px;'>";
							body += "<span>" + attachment.fileName + " : " + attachment.fileSize.toFixed(2) + " " + attachment.sizeUnit + "</span></div>"; */
						}
						text += "       </div>";
						body += text;
					}
					body+="</div>";
					
					$("#body-details").append(body);
					$("#body-details").show();
					$("#inbox").hide();
				    $("#details").show();
				} else {
					showEmailResponse(true, "error while reading email");
				}
				$("#mailLoading").removeAttr("style");
			}
		});
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
	location.replace(sourceleadUrl+"/composeMail?UUID="+uuid + "&mailType='reply'");  
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

<script type="text/javascript">
	$(document).ready(function() {
		$(".chosen-drop").hide();
		
		if("${emailAccountSettingsServiceList.size()}" === 0 || mailType === "hotcampaigns") {
			getMails('Inbox');
		} else {
			$($(".userMailList li")[0]).find("p").click();
		}
		/* $($(".newMailSet").find("li")[0]).find("a").click(); */
	});
	
	function selectOrDeselectAllEmails(ele) {
		var flag = $(ele).is(":checked");
		$("#emailMailDetailsPagination tr").each(function () {
			$(this).find("input[type='checkbox']").prop("checked", flag);
		});
		showOrHideMailControls();
	}
	
	function showOrHideMailControls() {
		var flag = false;
		$("#emailMailDetailsPagination tr").each(function () {
			if($(this).find("input[type='checkbox']").is(":checked")) {
				flag = true;
				return;
			}
		});
		if(flag) {
			if($("#mailboxLabel").html().toLowerCase() === "trash") {
				$(".deleteForever").show();
				$(".moveToTrash").hide();				
			} else {
				$(".moveToTrash").show();
				$(".deleteForever").hide();			
			}
			$("#DynamicMailControlsMaster").show();
		} else {
			$("#DynamicMailControlsMaster").hide();
		}	
	}
	
	function moveEmailsToTrash(deleteForever) {
		var messageIds = [];
		$("#emailMailDetailsPagination tr").each(function () {
			var cb = $(this).find("input[type='checkbox']").is(":checked");
			if($(this).find("input[type='checkbox']").is(":checked")) {
				messageIds.push($(this).data("id"));
			}
		});
		moveEmailToTrash(activeAccountId, messageIds, activeFolder, activeFolderShortName, deleteForever);
	}
	
	function moveEmailToTrash(emailAccountId, messageIds, folderName, folderShortName, deleteForever) {
		/* alert("EmailAccountId : " + emailAccountId + " Delete Mail : " + messageIds + " in folder : '" + folderName + "'"); */
		 $("#composeLoading").show();
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/deleteEmailsByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&messageIds=" + messageIds + "&folderName=" + folderName + "&deleteForever=" + deleteForever,
				success : function(data) {
					if(data && data != null && data.response === "SUCCESS") {
						readFolder(folderShortName, folderName, emailAccountId, 0, 10, 0);
						showEmailResponse(false, (messageIds.length > 1 ? "Emails" : "Email") + (deleteForever ? " Deleted Permanently" : " moved to Trash"));
					} else {
						showEmailResponse(true, data.responseMessage)
					}
					$("#composeLoading").hide();
				}
			});
	}
	
/* 	function deleteEmailsForEver() {
		alert("Delete multiple mail forever")
	}
	function deleteEmailForEver(emailAccountId, messageId, folderName) {
		alert("Delete mail forever")
	} */
	function showComposeEmailPopup() {
		refreshComposeMailFields();
		var fromEmail = $(".userMailList li[class='Act']").find("p").html();
		$("#fromEmail").val(fromEmail);
		$("#fromEmail").trigger("chosen:updated");
		/* $('.side_mask').show(); */
		$('#dynamicmailcompose').show();
		$("body").css("overflow-y","hidden");
	}
	function closeComposeEmailPopup() {
		refreshComposeMailFields();
		$('.side_mask').hide();
		$('#dynamicmailcompose').hide();
		$("#sendEmail").show();
		$("#replyEmail").hide();
		$("#forwardEmail").hide();
		$(".headerAdsrch span").html("Send Email");
		$("body").css("overflow-y","auto");
	}
	function refreshComposeMailFields() {
		$("#fromEmail").val("");
		$("#fromEmail").prop("disabled", false);
		$("#fromEmail").trigger("chosen:updated");
		$("#emailsList").val("");
		$("#emailsList").trigger("chosen:updated");
		$("#ccList").val("");
		$("#ccList").trigger("chosen:updated");
		$("#sendSubject").val("");
		nicEditors.findEditor('bodytext').setContent("");
		$("#mailAttachmentsDiv").empty();
	}
	
	/* function to show compose email popup with auto-filled values required for reply / replyAll */
	function showReplyEmailPopup(emailAccountId, messageId, folderName, replyToAllFlag) {
		$("body").css("overflow-y","hidden");
		$("#composeLoading").show();
		refreshComposeMailFields();
		var activeEmail = $("li[class='Act']").find("p").html();
		$("#fromEmail").prop("disabled",true);
		$("#fromEmail").trigger("chosen:updated");
/* 		var emailDetails = emailMap.get(messageId);
		if(emailDetails !== undefined && emailDetails !== null) { */
		if(emailDetails !== null) {
			$("#fromEmail").val(activeEmail);
			$("#fromEmail").trigger("chosen:updated");
			$("#emailsList").append("<option selected value='" + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
			$("#emailsList").trigger("chosen:updated");
			if(replyToAllFlag) {
				if(emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;").indexOf(activeEmail) === -1)
					$("#ccList").append("<option selected value='" + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
				
				if(emailDetails.cc !== null && emailDetails.cc !== "") {
					for(var e in emailDetails.cc.split(";")) {
						var ccMail = emailDetails.cc.split(";")[e].trim().replace(/</g, "&lt;").replace(/>/g,"&gt;");
						if(ccMail.indexOf(activeEmail) === -1)
							$("#ccList").append("<option selected value='" + ccMail + "'>" + ccMail + "</option>");
					}
				}
				$("#ccList").trigger("chosen:updated");
			}
			$("#sendSubject").val("RE: " + emailDetails.subject);
			
			var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
			text += "	<br/>";
			text += "	<br/>";
			text += "	-----Original Message-----<br/>";
			text += "	From: " + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Sent: " + emailDetails.sentDate + "<br/>";
			text += "	To: " + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			if(emailDetails.cc !== null && emailDetails.cc !== "")
				text += "	Cc: " + emailDetails.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Subject: " + emailDetails.subject + "<br/>";
			text += "</p>";
			
			nicEditors.findEditor('bodytext').setContent(text + "<br/> " + emailDetails.body);
			
			$(".headerAdsrch span").html("Reply Email");
			$("#sendEmail").hide();
			$("#replyEmail").show();
 			$("#replyEmail").data("messageId", messageId);
			$("#replyEmail").data("folderName", folderName);
			/* $('.side_mask').show(); */
			$('#dynamicmailcompose').show();
			$("#composeLoading").hide();
		} else {
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid=" + messageId + "&folderName=" + folderName,
				success : function(data) {
					if(data && data != null) {
						$("#fromEmail").val(activeEmail);
						$("#fromEmail").trigger("chosen:updated");
						$("#emailsList").append("<option selected value='" + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
						$("#emailsList").trigger("chosen:updated");
						if(replyToAllFlag) {
							if(data.cc !== null && data.cc !== "") {
								if(emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;").indexOf(activeEmail) === -1)								
									$("#ccList").append("<option selected value='" + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
								for(var e in data.cc.split(";")) {
									var ccMail = data.cc.split(";")[e].trim().replace(/</g, "&lt;").replace(/>/g,"&gt;");
									if(ccMail.indexOf(activeEmail) === -1)
										$("#ccList").append("<option selected value='" + ccMail + "'>" + ccMail + "</option>");
								}
							}
							$("#ccList").trigger("chosen:updated");
						}
						$("#sendSubject").val("RE: " + data.subject);
						
						var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
						text += "	<br/>";
						text += "	<br/>";
						text += "	-----Original Message-----<br/>";
						text += "	From: " + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Sent: " + data.sentDate + "<br/>";
						text += "	To: " + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						if(data.cc !== null && data.cc !== "")
							text += "	Cc: " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Subject: " + data.subject + "<br/>";
						text += "</p>";
						
						nicEditors.findEditor('bodytext').setContent(text + "<br/> " + data.body);
						
						$(".headerAdsrch span").html("Reply Email");
						$("#sendEmail").hide();
						$("#replyEmail").show();
						$("#replyEmail").data("messageId", messageId);
						$("#replyEmail").data("folderName", folderName);
						/* $('.side_mask').show(); */
						$('#dynamicmailcompose').show();
					} else {
						showEmailResponse(true, "error while reading email");
					}
					$("#composeLoading").hide();
				}
			});
		}
	}
	
	/* function to send reply message */
	function replyEmailFromMailBox(sendType) {
		if ($('#email_form').validate(false, validationSettings)) {
			var messageId = $("#replyEmail").data("messageId");
			var folderName = $("#replyEmail").data("folderName");
			var sourceleadUrl = '${sourceleadUrl}';
			
			nicEditors.findEditor('bodytext').saveContent();
					
			var form = $('#email_form')[0];
		  	var formData = new FormData(form)
		  		
		  	var toEmails =  $('#emailsList').val();
		  		 
		  	if(toEmails == null) {
		  		showEmailResponse(false, "Provide atleast one email id");
		   	} else {
		   		var emailAccountId = $("#fromEmail option:selected").data("id");
		   		var from = $("#fromEmail option:selected").val();
				$("#composeLoading").show();
				$.ajax({
					type : "POST",
				  	data: formData,
				  	enctype: 'multipart/form-data',
				  	processData: false,
				  	contentType:false, 
				  	url : sourceleadUrl+"/sendEmailFromMailBox?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&from=" + from + "&sendType=" + sendType + "&messageId=" + messageId + "&folderName=" + folderName,
				  	success : function(result) {
				  		if(result && result.response === "SUCCESS") {				  			
					  		closeComposeEmailPopup();
				  			showEmailResponse(false, result.responseMessage);
				  		} else 
				  			showEmailResponse(true, result.responseMessage);
					  	$("#composeLoading").hide();
				  	},
				});
			}
			return true;
		} else {
			return false;
		}		
	}

	/* function to show compose email popup with auto-filled values required for forwarding */
	function showForwardEmailPopup(emailAccountId, messageId, folderName) {
		$("body").css("overflow-y","hidden");
		$("#composeLoading").show();
		refreshComposeMailFields();
		var activeEmail = $("li[class='Act']").find("p").html();
		$("#fromEmail").prop("disabled",true);
		$("#fromEmail").trigger("chosen:updated");
/* 		var emailDetails = emailMap.get(messageId);
		if(emailDetails !== undefined && emailDetails !== null) { */
		if(emailDetails !== null) {
			$("#sendSubject").val("FW: " + emailDetails.subject);
			var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
			text += "	<br/>";
			text += "	<br/>";
			text += "	-----Original Message-----<br/>";
			text += "	From: " + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Sent: " + emailDetails.sentDate + "<br/>";
			text += "	To: " + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			if(emailDetails.cc !== null && emailDetails.cc !== "")
				text += "	Cc: " + emailDetails.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Subject: " + emailDetails.subject + "<br/>";
			text += "</p>";
			
			$("#fromEmail").val(activeEmail);
			$("#fromEmail").trigger("chosen:updated");
			nicEditors.findEditor('bodytext').setContent(text + "<br/> " + emailDetails.body);

			if(emailDetails.filesList !== null) {
				for(var e in emailDetails.filesList) {
					var fileURL = emailDetails.filesList[e].url.replace("Email Attachments", "temp");
					var text = "<p>" + emailDetails.filesList[e].fileName;
					text += "<i onclick='removeAttachment(this);' class='fa fa-close' style='cursor: pointer;'></i>";						
					var fileName = emailDetails.filesList[e].url.substring(emailDetails.filesList[e].url.lastIndexOf("/") + 1 , emailDetails.filesList[e].url.length);
					text += "<input name='filesList[" + e + "].fileName' data-name='fileName' type='hidden' value='" + emailDetails.filesList[e].fileName + "'>";
					text += "<input name='filesList[" + e + "].url' data-name='url' type='hidden' value='" + (emailDetails.filesList[e].url + "/" + emailDetails.filesList[e].fileName) + "'>";
					text += "</p>";
					$("#mailAttachmentsDiv").append(text);
					
					/* refreshAttachmentsIndex(); */						
				}
			}
			
			$(".headerAdsrch span").html("Forward Email");
			$("#sendEmail").hide();
			$("#replyEmail").hide();
			$("#forwardEmail").show();
			/* $(".side_mask").show(); */
			$("#dynamicmailcompose").show();
			$("#composeLoading").hide();
		} else {
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid=" + messageId + "&folderName=" + folderName,
				success : function(data) {
					if(data && data != null) {
						$("#sendSubject").val("FW: " + data.subject);
						var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
						text += "	<br/>";
						text += "	<br/>";
						text += "	-----Original Message-----<br/>";
						text += "	From: " + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Sent: " + data.sentDate + "<br/>";
						text += "	To: " + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						if(data.cc !== null && data.cc !== "")
							text += "	Cc: " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Subject: " + data.subject + "<br/>";
						text += "</p>";
						
						nicEditors.findEditor('bodytext').setContent(text + "<br/> " + data.body);
						
						$(".headerAdsrch span").html("Forward Email");
						$("#sendEmail").hide();
						$("#replyEmail").hide();
						$("#forwardEmail").show();
						/* $('.side_mask').show(); */
						$('#dynamicmailcompose').show();
					} else {
						showEmailResponse(true, "error while reading email");
					}
					$("#composeLoading").hide();
				}
			});
		}
	}
	
	/* function for sending / forwarding emails */
	function sendEmailFromMailBox(sendType) {
		if ($('#email_form').validate(false, validationSettings)) {
			var sourceleadUrl = '${sourceleadUrl}';
					
			nicEditors.findEditor('bodytext').saveContent();
					
			var form = $('#email_form')[0];
		  	var formData = new FormData(form)
		  		
		  	var toEmails =  $('#emailsList').val();
		  	var emailBody = nicEditors.findEditor('bodytext').getContent();	 
		  	if(toEmails == null) {
		  		/* showEmailResponse("Provide atleast one email id"); */
		  		showEmailResponse(false, "Provide atleast one email id");
		   	} else if(emailBody === "" || emailBody === "<br>") {
		   		showEmailResponse(false, "Enter Email Body");
		   	} else {
		   		var emailAccountId = $("#fromEmail option:selected").data("id");
		   		var from = $("#fromEmail option:selected").val();
				$("#composeLoading").show();
				$.ajax({
					type : "POST",
				  	data: formData,
				  	enctype: 'multipart/form-data',
				  	processData: false,
				  	contentType:false, 
				  	url : sourceleadUrl+"/sendEmailFromMailBox?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&from=" + from + "&sendType=" + sendType,
				  	success : function(result) {
				  		if(result && result.response === "SUCCESS") {			  			
				  			setTimeout(function() {
				  				closeComposeEmailPopup();
					 		}, 1000);
				  			showEmailResponse(false, result.responseMessage);
				  		} else
				  			showEmailResponse(true, result.responseMessage);
					  	$("#composeLoading").hide();
				  	},
				});
			}
			return true;
		} else {
			return false;
		}
	}
</script>
<script type="text/javascript">
	bkLib.onDomLoaded(function() {
	    nicEditors.editors.push(
	    	new nicEditor({fullPanel: true}).panelInstance(document.getElementById('bodytext'))
	    );
	});

	function uploadAttachment() {
		var files = document.getElementById("attachfileBtn").files;
		var filesSize = files.length;
		/* $(ele).parent().parent().next().val(filesSize > 1 ? (filesSize + " files selected") : $(ele).val()); */
	
		var data = new FormData();
		for(var i=0 ; i < filesSize; i++){
			data.append("Email Attachments", files[i]);
		}
		data.append("type", "Email Attachments");
		$.ajax({
			url : sourceleadUrl + "/ws/uploadTaxDocs",
			data : data,
			processData : false,
			type : 'POST',
			contentType : false,
			success : function(data) {
				if (data && data.length > 0) {
					/* var filesText = ""; */
					for(var e in data) {
						var fileURL = data[e].fileURL.replace("Email Attachments", "temp");
						var text = "<p>" + data[e].fileName;
						text += "<i onclick='removeAttachment(this);' class='fa fa-close' style='cursor: pointer;'></i>";						
						var fileName = data[e].fileURL.substring(data[e].fileURL.lastIndexOf("/") + 1 , data[e].fileURL.length);
						text += "<input name='filesList[" + e + "].fileName' data-name='fileName' type='hidden' value='" + fileName + "'>";
						text += "<input name='filesList[" + e + "].url' data-name='url' type='hidden' value='" + data[e].fileURL + "'>";
						text += "<input name='filesList[" + e + "].newUpload' data-name='newUpload' type='hidden' value='true'>";
						text += "</p>";
						$("#mailAttachmentsDiv").append(text);
						
						refreshAttachmentsIndex();							
					}
				}
			},
			error : function(err) {
				showEmailResponse(true, err);
			}
		});
	}
	
	function refreshAttachmentsIndex() {
		var index = 0;
		$("#mailAttachmentsDiv p").each(function() {
			$(this).find("input[data-name='fileName']").attr("name", "filesList[" + index + "].fileName");
			$(this).find("input[data-name='url']").attr("name", "filesList[" + index + "].url");
			if($(this).find("input[data-name='newUpload']").length > 0)
				$(this).find("input[data-name='newUpload']").attr("name", "filesList[" + index + "].newUpload");
			index++;
		});
	}

	function removeAttachment(ele) {
		$(ele).parent().remove();
		refreshAttachmentsIndex();
	}
	
	function showEmailResponse(failedFlag, message){
		$("#emailResponseText").html(message);
		$("#textBoxmailerPopup").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		setTimeout(function() {
			$("#textBoxmailerPopup").slideUp(200);
			$(".project_inn_mask").fadeOut(200);
 		}, (failedFlag ? 3000 : 1500));
		/* $(".side_mask").fadeIn(200); */
	}
	
	function textBoxCancel(){
		$("#textBoxmailerPopup").slideUp(200);
		$(".project_inn_mask").hide();
		/* if($("#dynamicmailcompose").css("display") === "none")
			$(".side_mask").hide(); */
	}
	
	function getEmailSettings(settingsType) {
		$("#emailConfigurationPopup").empty();
		$.post(sourceleadUrl + "/emailSettings",{source : settingsType}, function(data) {
			$("#configureEmailDiv").hide();
			$("#emailConfigurationPopup").append(data);
		});
	}
	
	function deleteConMail1(id){
		if (confirm('Are you sure, you want to delete mail configuration?')) {
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/deleteEmailAccount?${_csrf.parameterName}=${_csrf.token}&id="+id,
				success : function(data) {
						location.replace(sourceleadUrl+"/mailBox/mails");
				},
			});
		}
	}
	
	function getConfiguredEmail(id) {
		$("#emailConfigurationPopup").empty();
		$.post(sourceleadUrl + "/getEmailConfiguration",{id : id}, function(data) {
			$("#configureEmailDiv").hide();
			$("#emailConfigurationPopup").append(data);
		});
	}
</script>	

 	
<jsp:include page="masterFooter.jsp" />