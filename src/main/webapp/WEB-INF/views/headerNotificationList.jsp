<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
function getNotificationsByuserId(){
	var userId = '${SourceLeadWeb_user.id}';
	var  orgCode ='${SourceLeadWeb_orgCode}';
	var locationCode = '${SourceLeadWeb_locationCode}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.get(sourceleadUrl+"/getNotificationsByuserId?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&locationCode="+locationCode+"&orgCode="+orgCode+"&orgLocationId="+orgLocationId, function(json) {
		var data = json.data
		var result = "";
		result = result + '<div class="header-widget alert-area">';
		result = result + '<ul class="list-unstyled">';
		if(data !=null && data !='' && data !='undefined'){
			for(var i=0;i<data.length;i++){
				var opt = data[i];
				result = result + ' <li>';
				result = result + '<div class="clearfix msg">';
				result = result + '<div class="msg-img"> <span>';
				if(opt.userType !=null && opt.userType !='' && opt.userType !='undefined' && opt.fileUrl !=null && opt.fileUrl !='' && opt.fileUrl !='undefined'){
				result = result + ' <img class="img-circle" title='+opt.firstName+' alt='+opt.userType+' onerror="predefineProfileImage(this);" src='+opt.fileUrl+'>';
				}
				result = result + ' </span> </div>';
				
				result = result + '<div class="msg-content">';
				if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID == null || opt.spocUUID == "" || opt.spocUUID == "undefined")){
					if(opt.mode == "read"){
						result = result +'<a class="visitedAnchor" href="'+opt.actionLink+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a>';
					}else{
						result = result +'<a href="'+opt.actionLink+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a> ';
					}
				}else if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID != null && opt.spocUUID != "" && opt.spocUUID != "undefined") && (opt.spocInvitationStatus == "Accepted" || opt.spocInvitationStatus == "Rejected") && (opt.orgRelationUUID != null && opt.orgRelationUUID != "" && opt.orgRelationUUID != "undefined") && opt.responderType == "vendor"){
					if(opt.mode == "read"){
						result = result +'<a class="visitedAnchor" href="/app/vendorsList?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'"  onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a>';
					}else{
						result = result +'<a href="/app/vendorsList?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a> ';
					}
				}else if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID != null && opt.spocUUID != "" && opt.spocUUID != "undefined") && (opt.spocInvitationStatus == "Accepted" || opt.spocInvitationStatus == "Rejected") && (opt.orgRelationUUID != null && opt.orgRelationUUID != "" && opt.orgRelationUUID != "undefined")&& opt.responderType == "client"){
					if(opt.mode == "read"){
						result = result +'<a class="visitedAnchor" href="/app/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a> ';
					}else{
						result = result +'<a href="/app/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a> ';
					}
					
				}else{
					if(opt.mode == "read"){
						result = result +'<a class="visitedAnchor" href="#" onclick="linkReadAndUnread(\''+opt.uuId+'\');" title="'+opt.body+'"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span> ';
					}else{
						result = result +'<a href="#"  title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
						result = result + ' <span>'+opt.calDays+'</span></a> ';
					}
					
				}
				
				result = result + '</div>';
				result = result + ' </div>';
				result = result + ' </li>';
			} 
			
		}else{
			result = result + '<div class="clearfix msg_blank">All Caught UP!!! </div>'
		}
          result = result + '</ul>';
         result = result + '<div class="headerwidget-footer"> <a href="<c:url value="/app/notificationsList"/>">View All</a> </div>';
          result = result + ' </div>';
		
		$("#ndiv").empty();
      $("#ndiv").append(result);
      $(".alert-area").toggleClass("notiActive");
		
	});
}
	
$('html').click(function(){
	$(".alert-area").removeClass("notiActive");
});

$('.alert-area, .notiClick').click(function(event){
	  event.stopPropagation();
});

</script>
<script>
function linkReadAndUnread(uuId){
	 $.ajax({
	      type: "POST",
	  	  url: sourceleadUrl+"/notificationStatusRead?${_csrf.parameterName}=${_csrf.token}&uuId="+uuId,
	  			  success :function(data) {
     			 }							     
	}); 
}
</script>