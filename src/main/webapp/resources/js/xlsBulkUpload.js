var xlsupload = new Xlsupload();

function Xlsupload(){
	
		this.getFailureUploadStats = function(entityType,dateFormat){
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getBulkImportsFailureUploadList?"+tokenParamName+"="+tokenValue+"&entityType="+entityType, 
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
	        	   result = result + '   <div class="clearfix"></div>';
		           result = result + '   </div>';
		           result = result + '   <div class="">';
		           result = result + '     <table class="display" cellspacing="0" width="100%">';
		           result = result + '       <thead>';
		           result = result + '         <tr>';
		           result = result + '           <th>Date<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
		           result = result + '           <th>Time</th>';
		           result = result + '           <th>Failure Count</th>';
		           result = result + '           <th>Failure Attachment</th>';
		           result = result + '           <th>Uploaded File</th>';
		           result = result + '           <th>Action</th>';
		           result = result + '         </tr>';
		           result = result + '       </thead>';
		           result = result + '       <tbody>';
	        	
	  				if(json != null && json != '' && json.length > 0){
		        	   
		        	   var progressRow = '';
		        	   for(var i=0; i<json.length; i++){
		        		   var report = json[i];
		        		   var url = sourceleadUrl+"/dumpBulkImportsFailureList?&documentKey="+report.documentKey;
		        		   if(report.uploadProcessingStatus == 'Inprogress'){
		        			   progressRow = json[i];
		        			   $("#upload_button").attr("disabled", "disabled");
		        		   }
		        		    
		        		    result = result + '<tr>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.time+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-100">'+report.failureCount+'</div></td>';
		        		   
		        		    result = result+'<td>';
		        		    result = result+'<div class="tbldata-dotdot-me">'; 
		        		    result = result+'<a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+url+'\"  title="Download"></a>';
		        		    result = result+'</div></td>';
		        		    
		        		    result = result+'<td>';
		        		    result = result+'<div class="tbldata-dotdot-me">'; 
		        		    result = result+'<a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+report.fileUrl+'\"  title="Download"></a>';
		        		    result = result+'</div></td>';
		        		    
		        		    result = result+'<td>';
		        		    result = result+'<div>'; 
		        		    result = result+'<a href="#" onclick=""><i class="fa fa-trash text-danger" aria-hidden="true" alt="Delete" title="Delete"></i></a>';
		        		    result = result+'</div></td>';
		        		   
		        		    result = result + '</tr>';
			    		}
				           result = result + '</tbody>';
				           result = result + '</table>';
				           result = result + '</div>';
				           result = result + '</div>';
			    			
			    			$("#uploadXlsHistoryDiv").empty();
			    			$("#uploadXlsHistoryDiv").append(result);
			    			$("#uploadXlsHistoryDivTitle").removeAttr('hidden');
		           }
	        	
	        	
			 },
	        error: function(e){
	        }
	    });
		
	}
		
	
}