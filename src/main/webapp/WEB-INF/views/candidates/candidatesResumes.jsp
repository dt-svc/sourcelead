<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%-- 
     @author : Venkat Kesav
     this script is for get candidate resumes list.
     1.usage     : onload
     2.parameter : candidateId.
--%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
#uploadFileGroup p.jquery_form_error_message{
	position: absolute;
	left:0;
	bottom:-16px;
	}
	.tblicons a{
	font-size:13px;
	}
	.tblicons a:first-child{
	margin-left: 0;
	}
	.tblicons .glyphicon-trash{
	margin-left: 5px;
	}
	.tblicons a{
	margin:0px 8px;
	padding: 0 !important;
	} 
	.vendor_wrap_inn_table table tr th:last-child{
	width:100px;
	}
	.removeIcon{
	font-size:22px; position:relative; top:-5px; margin-left:15px; color:#F22613; cursor: pointer;
	}
	.tblicons_4 {
	width:100px;
	}
	.tblicons_4 a {
	margin: 0 2px;
	}
</style>

<script>

</script>

<!-- Show success message after form submission script -->
<script>
	function resMsgFadeinResumes() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->

<%-- 
     @author : Venkat Kesav
     this script is for refresh Resume List
     1.usage     : onclick
--%>
<script>
function closeResumePopup(){
	 $("#popUpDivResume").removeClass("edit-details-active");
	 $(".location-alert").fadeOut();
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for add resume popup
     1.usage     : onload
--%>
<script>
function refreshResumeList(candidateId){
	candidateResumesList(candidateId);
}
</script>

<div id="deleteResumePopUp"></div> 

<%-- 
     @author : Venkat Kesav
     this script is for add resume popup
     1.usage     : onload
     2.parameter : candidateId.
--%>
<script>

</script>
	
	 
  
<%-- 
     @author : Venkat Kesav
     this script is for saving resume
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	
		
</script>

<%-- 
     @author : Venkat Kesav
     this script is for prepare list of resumes
     1.usage     : onload
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
 
</script>

<div class="ifraMask"></div>
<div class="framViePop"> <i class="fa fa-times fraClose" onclick="fraclose();"></i>
<iframe id="myFrame" width="100%" height="100%" frameborder="0" allowfullscreen></iframe>
</div>

<%-- 
     @author : Venkat Kesav
     this script is for terminate resume (inactive status)
     1.usage     : onclick
     2.parameter : candidateId, resumeId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
function getCandiResume(fileURL,title){
	if(fileURL != null && fileURL != ""){
		window.open(atsUrl+'/viewDocumentsForCan/'+title+'?docFile='+encodeURI(fileURL),title,"height=600,width=600");
	}  
}
function getIframeDocument(element)
{
	var doc = element.contentDocument;
        if (doc == undefined || doc == null)
        {
         doc = element.contentWindow.document;
        }

	return doc;
} 
function fraclose(){
	$(".ifraMask").fadeOut(200);
	$(".framViePop").hide();
	
/* var win = window.open();
win.document.write('<iframe width="560" height="315" src="//www.youtube.com/embed/mTWfqi3-3qU" frameborder="0" allowfullscreen></iframe>') */

}

function deleteResumePopup(){
	$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteEduPopUp"></div> 

<script type="text/javascript">

</script>
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<!-- <script>
	function clearUpload(){
		$("#uploadFile").val("");
	}
</script> -->