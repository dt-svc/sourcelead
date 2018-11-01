var xlsupload = new Xlsupload();

function Xlsupload(){
	
		this.getUploadInprogressProgressBar = function(entityType,dateFormat){
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getXlsProcessingHistroyListByEntityType?"+tokenParamName+"="+tokenValue+"&entityType="+entityType, 
	        success: function(json) {
	        	
	   		  if(dateFormat.match("yyyy-MM-dd")){
	   			  uploadedDateStr = 'YYYY-MM-DD';
	   		  }if(dateFormat.match("dd-MM-yyyy")){
	   			uploadedDateStr = 'DD-MM-YYYY'; 
	   		  }if(dateFormat.match("MM-dd-yyyy")){
	   			uploadedDateStr = 'MM-DD-YYYY'; 
	   		  }if(dateFormat.match("yyyy-dd-MM")){
	   			uploadedDateStr = 'YYYY-DD-MM'; 
	   		  }
	        	
	        	   var result = "";
	        	   result = result + '     <div class="clearfix"></div>';
		           result = result + '   </div>';
		           result = result + '   <div class="">';
		           result = result + '     <table class="display" cellspacing="0" width="100%">';
		           result = result + '       <thead>';
		           result = result + '         <tr>';
		           result = result + '           <th>File Name</th>';
		           result = result + '           <th>Uploaded</th>';
		           result = result + '           <th>Success</th>';
		           result = result + '           <th>Failed</th>';
		           result = result + '           <th>Date / Time<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
		           result = result + '           <th>Status</th>';
		           result = result + '         </tr>';
		           result = result + '       </thead>';
		           result = result + '       <tbody>';
	        	
	  				if(json != null && json != '' && json.length > 0){
		        	   
		        	   var progressRow = '';
		        	   for(var i=0; i<json.length; i++){
		        		   var report = json[i];
		        		   
		        		   if(report.uploadProcessingStatus == 'Inprogress'){
		        			   progressRow = json[i];
		        			   $("#upload_button").attr("disabled", "disabled");
		        		   }
		        		    
		        		    result = result + '<tr>';
		        		    result = result + '<tr>';
		        		    result = result + '<td><div class="tbldata-dotdot-big text-center"><span title="'+report.fileName+'">'+report.fileName+'</span></div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot_100">'+report.totalCount+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-100">'+report.successCount+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-100">'+report.failureCount+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+'&nbsp;&nbsp;'+report.time+'</div></td>';
		        		    
		        		    	 if(report.uploadProcessingStatus == 'Completed'){
		        		    		 if(report.fileUrl != null && report.fileUrl != '' && report.fileUrl != 'undefined'){
			        		    		  result = result+'<td><div class="tbldata-dotdot-50 1538051356458tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+report.fileUrl+'\" download title="Download"></a>';
			        		    	  }else{
			 	        		    	 result = result + '<td><div class="tbldata-dotdot_100"><span class="text-danger">Uploaded Empty file</span></div></td>';
			  	        		       }
				        		    }else{
				        		    
				        		    	  result = result + '<td>';
				         		    	  result = result + '<div class="browseProgress">';
				         		    	  result = result + '<div class="progress progress-striped active">';
				         		    	  result = result + '<div class="progress-bar progress-bar-success" id="progressBar">0%</div>';
				         		    	  result = result + '</div>';
				         		    	  result = result + '<div class="progressCount">Uploading <span id="currentUploadedCount"></span> of <span id="totalUploadedCount"></span></div>';
				         		    	  result = result + '</div>';
				                          result = result + '</td>';
			                        
				        		    } 
		        		   
		        		   
		        		 
			    		}
				           result = result + '       </tbody>';
				           result = result + '       </table>';
				           result = result + '</div>';
				           result = result + '</div>';
			    			
			    			$("#uploadXlsHistoryDiv").empty();
			    			$("#uploadXlsHistoryDiv").append(result);
			    			$("#uploadXlsHistoryDivTitle").removeAttr('hidden');
			    			
			    			if(progressRow != ''){
			    				  $(".browseProgress").show();
		                  		  $("#progressBar").html('0%');
		                  		  $(".progress div").css("width", "0%");
		                  		  getXlsInProgressBar(progressRow);
			    			}else{
			    				 uploadflag = false;
			    			}
		           }
	        	
	        	
			 },
	        error: function(e){
	        }
	    });
		
	}


	function getXlsInProgressBar(json){
		
		var totalCount = json.totalCount == undefined? 0: json.totalCount;
		var succesCount = json.successCount == undefined? 0: json.successCount;
		var failureCount = json.failureCount == undefined? 0: json.failureCount;  
		var uploadingCount = parseInt(succesCount) + parseInt(failureCount);
		
		percentComplete = parseInt(uploadingCount)/parseInt(totalCount);
		$(".progress div").css("width", parseInt(percentComplete*100) + "%");
		$("#progressBar").html(parseInt(percentComplete*100)+'%');
		$("#totalUploadedCount").html(totalCount);
		$("#currentUploadedCount").html(uploadingCount);
			
	}
}