<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<style>
#tab-7 .comments-content textarea {
	width: 957px;
    border: none;
}
</style>

<script>
function candidateCommentsList(candidateId,submissionId){
	$('#tab-7').show();
	
	$('.' + "current").removeClass("current");
	$("#candidateCommentTab").addClass("current");
	var urlForm = "";
	if(submissionId != null && submissionId != '' && submissionId != 'undefined'){
		urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&submissionId="+submissionId;
    }else{
    	urlForm = "/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId;
    }
	
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
        	 var result = "";
              if(data != null && data != 'undefined' && data != "" && data != '[]'){
              	 	 var json = JSON.parse(data);
              	 		  result  = result + '   <div class="project_tabs_inn_head addUser-org"> ';
            			  result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
            	          for(var i=0;i<json.length;i++){
            	            	var report = json[i];
            	        	  	result = result+'<div class="comments-div comments-inner clearfix">';
            	        	    result = result+'    <div class="commentsimg">';
            	        	    result = result+'    <i class="fa fa-comments"></i>';
            	        	    result = result+'        <div class="commentsimg-inner">';
            	        	    result = result+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.commentAuthorUserId+'"/>';
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
	              }else{
	 	    	         var result ="";
	 	    	        result = result+"   <div class='dataTables_empty'>"; 
	 	    	       /*  result = result+"   <div class='row'>"; 
						 result = result+"	<div class='col-lg-12'>"; 
						 result = result+"		<div class='content-area clearfix'>"; 
						 result = result+"			<div class='blank-pages'>";
						 result = result+"			        <p><i class='fa fa-5x fa fa-tasks'></i></p>";
						 result = result+"			        <p>No Comments Found</p>";
						 result = result+"			 </div>";
						 result = result+"		</div>"; 
						 result = result+"	</div>"; 
						 result = result+"  </div> ";  */
						 result = result+"  </div> ";
	                }
          	  result  = result + '              </div>';
          	  result  = result + '        </div>';
          	  
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
              $("#tab-7").append(result);
              $("#tab-8").empty();
         },
         
      });

	/* result  = result + '<div class="project_tabs_inn_head">';
	result  = result + '              <input class="vendorfild" type="text"><i class="fa fa-search"></i> </div>';
	result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	 */
    
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
  