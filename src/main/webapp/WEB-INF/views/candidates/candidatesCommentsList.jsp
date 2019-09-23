<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
</script>

<script>
function candidateCommentsList(candidateId,submissionId,candType){
	$('#tab-7').show();
	$('.' + "current").removeClass("current");
	$("#candidateCommentTab").addClass("current");
	var urlForm = "";
	if(submissionId != null && submissionId != '' && submissionId != 'undefined' && submissionId != 'null') {
		urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&submissionId="+submissionId;
    } else {
    	urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateType="+candType;
    }
	$.ajax({
		type : "POST",
	    url: atsUrl+urlForm,        
	    success :function(data) {
	    	var json = data.data;
	    	if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
	    	    showError(data.responseMessage);
	    	} else if (data.response === "AWKWARD") {
	    	    location.href = "/errorPage";
	    	} else if (data.response === "SUCCESS") {
	    		var result = "";
				if(json.length > 0) {
		       	 	result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
		     		result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		     	    for(var i=0;i<json.length;i++) {
		     	    	var report = json[i];
		     	        result = result+'<div class="comments-div comments-inner clearfix">';
		     	        result = result+'    <div class="commentsimg">';
		     	        result = result+'    <i class="fa fa-comments"></i>';
		     	        result = result+'        <div class="commentsimg-inner">';
		     	        result = result+'        	<img onerror="predefineProfileImage(this);" src="'+report.commentAuthorUserImage+'"/>';
		     	        result = result+'        </div>';
		     	        result = result+'    </div>';
		     	        result = result+'     <div class="comments-content">';
		     	        result = result+'         <div class="pull-left">';
		     	        result = result+'         <p class="comment-by">';
		     	        result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
		     	        result = result+'            <span class="small">('+report.commentTime+')</span></p>';
		     	        result = result+'         <textarea class="commentText" readonly>'+report.comment+'</textarea>';
		     	        result = result+'         </div>';
		     	        result = result+'     </div>';
		     	        result = result+'</div>';
		     		}
		     	   result  = result + '              </div>';
			       	result  = result + '        </div>';
		        } else {
					var result ="";
			        result = result+"   <div class='dataTables_empty'>"; 
					result = result+"  </div> ";
				}
		       	
				$("#tab-1").empty();
		        $("#tab-2").empty(); 
		        $("#tab-3").empty();  
		        $("#tab-4").empty();  
		        $("#tab-5").empty();  
		        $("#tab-6").empty();
		        $("#tab-7").empty();
		        $("#tab-9").empty();
		        $("#tab-10").empty();
		           
		        $("#tabb-1").empty();
		       	$("#tabb-2").empty(); 
		       	$("#tabb-3").empty();
		       	$("#tabb-4").empty();  
		        $("#tabb-5").empty();  
		        $("#tabb-6").empty();
		        $("#tabb-8").empty();
		        $("#tabb-9").empty();
		        $("#tab-7").append(result);
		        $("#tab-8").empty();
	    	} else if (data.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	      },
	   });
}
</script>

<script type="text/javascript">
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<!-- this script is for serializeFormJSON -->
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

<script>
function commentsPagination(){  
    $('#candidateCommentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 }
</script>