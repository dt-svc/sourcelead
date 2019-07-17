


<script>
function getTemplateOverViewDetails(assignedUUID){
	  document.getElementById("feedbackWidget").removeAttribute("class");
	  document.getElementById("goalsWidget").removeAttribute("class");
      document.getElementById("tasksWidget").removeAttribute("class");
      document.getElementById("finalScoreWidget").removeAttribute("class");
      
      $("#performanceAppraisalDiv").empty(); 
    
	$("#appraisalLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		  type: "GET",
		    url: pmsUrl+"/getSelfAppraisalTemplateDetails?assignedUUID="+assignedUUID,
		     success :function(response) {
		    	 
			 		if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	  	    		    showError(response.responseMessage);
	  	    		    $("#appraisalLoading").removeAttr('style');
	  	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		
		        		var reportingManagerImgUrl = '';
		        		if(response.object.appraisalTemplate.reportingManagerImgUrl != null){
		        			reportingManagerImgUrl = response.object.appraisalTemplate.reportingManagerImgUrl;
		        		}else{
		        			reportingManagerImgUrl = '/resources/images/userprofileImg.jpg';
		        		}
		        		
		        		var resourceManagerImgUrl = '';
		        		if(response.object.appraisalTemplate.resourceManagerImgUrl != null){
		        			resourceManagerImgUrl = response.object.appraisalTemplate.resourceManagerImgUrl;
		        		}else{
		        			resourceManagerImgUrl = '/resources/images/userprofileImg.jpg';
		        		}
		        		
		        		
		        		
		        		  var result = "";
				    	  result = result + '<div class="clearfix goalssummar_widget">';
				    	  result = result + '<div class="clearfix">';
				    	  result = result + '<div class="form-group clearfix">';
				    	  result = result + '<label class="col-md-2 paddingRight">Appraisal Name<span class="pull-right paddingLeft">:</span></label>';
				    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.name+'</div>';
				    	  result = result + ' </div>';
				    	  result = result + '<div class="form-group clearfix">';
				    	  result = result + '<label class="col-md-2 paddingRight">Process Period<span class="pull-right paddingLeft">:</span></label>';
				    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.appraisalProcessStartDate+'<span class="text-muted ml mr">to</span>'+response.object.appraisalTemplate.appraisalProcessEndDate+'</div>';
				    	  result = result + '</div>';
				    	 
			    		  result = result + '<div class="form-group clearfix">';
				    	  result = result + '<label class="col-md-2 paddingRight">Due Date<span class="pull-right paddingLeft">:</span></label>';
				    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.selfAppraisalEndDate+'</div>';
				    	  result = result + '</div>';
			    	  
			    		  result = result + '<div class="form-group clearfix">';
				    	  result = result + '<label class="col-md-2 paddingRight">Due Date<span class="pull-right paddingLeft">:</span></label>';
				    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.peerReviewEndDate+'</div>';
				    	  result = result + '</div>';
				    	  
				    	  
 							if(response.object.peersList.length > 0 ){
				    		  
 								  result = result + '<div class="form-group clearfix">';
 						    	  result = result + '<label class="col-md-2 paddingRight mt10">Peers<span class="pull-right paddingLeft">:</span></label>';
 						    	  result = result + '<div class="col-md-10">';
 						    	  result = result + '<ul class="list-inline list-unstyled selfAppraisalpeersList mb0">';
 						    	 
 						    	 for (var i=0; i<response.object.peersList.length; i++)
 									{
 						    		  var opt = response.object.peersList[i];
 						    		  var peerUserImgUrl = '';
 						    		  if(opt.userImgUrl != null){
 						    			 peerUserImgUrl = opt.userImgUrl;
 						    		  }else{
 						    			 peerUserImgUrl = '/resources/images/userprofileImg.jpg';
 						    		  }
 						    		  result = result + '<li>';
 	 						    	  result = result + '<div class="selfAppraisalImg">';
 	 						    	  result = result + '<img src="'+peerUserImgUrl+'" alt="Image">';
 	 						    	  result = result + '</div>';
 	 						    	  result = result + '<span class="selfAppraisalpeerName">'+opt.associateName+'</span>';
 	 						    	  result = result + '</li>';
 									}

 						    	  result = result + '</ul>';
 					                    
 						    	  result = result + '</div>';
 						    	  result = result + '</div>';
				    	    }
 							
 							
 
				    	  if(response.object.appraisalTemplate.reportingManager != null){
				    		  
				    		  result = result + '<div class="form-group clearfix">';
					    	  result = result + '<label class="col-md-2 paddingRight mt10">Reporting Manager<span class="pull-right paddingLeft">:</span></label>';
					    	  result = result + '<div class="col-md-5">';
				                      
					    	  result = result + '<div class="selfAppraisalImg">';
					    	  result = result + '<img src="'+reportingManagerImgUrl+'" alt="Image">';
					    	  result = result + '</div>';
					    	  result = result + '<span class="selfAppraisalpeerName">'+response.object.appraisalTemplate.reportingManager+'</span>';
					    	  result = result + '</div>';
					    	  result = result + '</div>';
					    	  
					    	  if(response.object.appraisalTemplate.reportingManagerReviewStartDate != null){
						    	  result = result + '<div class="form-group clearfix">';
						    	  result = result + '<label class="col-md-2 paddingRight">Review Peirod<span class="pull-right paddingLeft">:</span></label>';
						    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.reportingManagerReviewStartDate+' <span class="text-muted ml mr">to</span> '+response.object.appraisalTemplate.reportingManagerReviewEndDate+'</div>';
						    	  result = result + '</div>';
						    	  }
				    	  }

				    	 
				    	  if(response.object.appraisalTemplate.resourceManager != null){
				        	  result = result + '<div class="form-group clearfix">';
					    	  result = result + '<label class="col-md-2 paddingRight mt10">Resource Manager<span class="pull-right paddingLeft">:</span></label>';
					    	  result = result + '<div class="col-md-5">';
					    	  
					    	  result = result + '<div class="selfAppraisalImg">';
					    	  result = result + '<img src="'+resourceManagerImgUrl+'" alt="Image">';
					    	  result = result + '</div>';
					    	  result = result + '<span class="selfAppraisalpeerName">'+response.object.appraisalTemplate.resourceManager+'</span>';
					    	  result = result + '</div>';
					    	  result = result + '</div>';
				  
					    	  if(response.object.appraisalTemplate.resourceManagerReviewStartDate != null){
					    		  result = result + '<div class="form-group clearfix">';
						    	  result = result + '<label class="col-md-2 paddingRight">Review Peirod<span class="pull-right paddingLeft">:</span></label>';
						    	  result = result + '<div class="col-md-5">'+response.object.appraisalTemplate.resourceManagerReviewStartDate+' <span class="text-muted ml mr">to</span> '+response.object.appraisalTemplate.resourceManagerReviewEndDate+'</div>';
						    	  result = result + '</div>';
					    	  }
				    	  }
				
				    	  

						 if(response.object.appraisalTemplate.description != null 
								 && response.object.appraisalTemplate.descriptionDisplay != null 
								 && response.object.appraisalTemplate.descriptionDisplay == 'Y'){
							 result = result + '<div class="form-group clearfix">';
					    	  result = result + '<label class="col-md-2 paddingRight">Description<span class="pull-right paddingLeft">:</span></label>';
					    	  result = result + '<div class="col-md-10">'+response.object.appraisalTemplate.description+'</div>';
					    	  result = result + '</div>';
						 }	
				    	 

				    	  result = result + '</div>';
				    	  result = result + '</div>';
				    	  
			    	      $("#performanceAppraisalDiv").append(result);
			    	      
			    	      $("#appraisalLoading").removeAttr("style");
		        		
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		 		
				},
			}); 
	
}
</script>

