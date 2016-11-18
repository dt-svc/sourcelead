<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">


<script>
function candidateCommentsList(candidateId){
	$("#tab-7").show();
	$('.current').each(function() {
		    var currentId = this.id;
		    $("#"+currentId).removeClass("current");
	});
	$("#candidateCommentTab").addClass("current");
	
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {
        	 var result = "";
              if(data != null && data != 'undefined' && data != ""){
              	 	 var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			  result  = result + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
            	          for(var i=0;i<json.length;i++){
            	            	var report = json[i];
            	        	  	result = result+'<div class="comments-div clearfix">';
            	        	    result = result+'    <div class="commentsimg">';
            	        	    result = result+'    <i class="fa fa-comments"></i>';
            	        	    result = result+'        <div class="commentsimg-inner">';
            	        	    /* result = result+'        	<img onerror="predefineProfileImage(this);"  src="<c:url value="/displayUserImage/'+report.commentAuthorUserId'+"/>" alt="User Comment" title="User Comment">'; */
            	        	    result = result+'        	<img onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>">';
            	        	    result = result+'        </div>';
            	        	    result = result+'    </div>';
            	        	    result = result+'     <div class="comments-content">';
            	        	    result = result+'         <div class="pull-left">';
            	        	    result = result+'         <p class="comment-by">';
            	        	    result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
            	        	    result = result+'            <span class="small">('+report.commentTime+')</span></p>';
            	        	    result = result+'         <p class="comment-text">'+report.comment+'</p>';
            	        	    result = result+'         </div>';
            	        	    result = result+'     </div>';
            	        	    result = result+'</div>';
            	          }
            		 }
	              }else{
	               	 	 $("#tab-7").removeAttr("style");
	 	    	         var result ="";
			    	     result = result+"   <div class='row'>"; 
						 result = result+"	<div class='col-lg-12'>"; 
						 result = result+"		<div class='content-area clearfix'>"; 
						 result = result+"			<div class='blank-pages'>";
						 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						 result = result+"			        <p>No Comments Found</p>";
						 result = result+"			 </div>";
						 result = result+"		</div>"; 
						 result = result+"	</div>"; 
						 result = result+"  </div> "; 
	                }
          	  result  = result + '              </div>';
          	  
              $("#tab-1").empty();
              $("#tab-2").empty(); 
              $("#tab-3").empty();  
              $("#tab-4").empty();  
              $("#tab-5").empty();  
              $("#tab-6").empty();
              $("#tab-7").empty();
              $("#tab-7").append(result);
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
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
 
 }
</script>
  