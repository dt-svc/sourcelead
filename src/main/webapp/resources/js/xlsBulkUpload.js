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
		        	   
		        	   for(var i=0; i<json.length; i++){
		        		   var report = json[i];
		        		   var url = '';
		        		   if(report.failureFileUrl != '' && report.failureFileUrl != null && report.failureFileUrl != 'undefined'){
		        			   url =  report.failureFileUrl;
		        		   }else{
		        			   url = sourceleadUrl+"/dumpBulkImportsFailureList?&documentKey="+report.documentKey;
		        		   }
		        		    
		        		    result = result + '<tr id="failure'+report.documentKey+'">';
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
		        		    result = result+'<a href="#" onclick="xlsupload.deleteFailureRecords(\''+report.documentKey+'\',\''+entityType+'\',\''+dateFormat+'\');" ><i class="fa fa-trash text-danger" aria-hidden="true" alt="Delete" title="Delete"></i></a>';
		        		    result = result+'</div></td>';
		        		   
		        		    result = result + '</tr>';
			    		}
				          
	  				}else{
		    			 result = result + '<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty"></td></tr>';
		    		}
	  				
	  				   result = result + '</tbody>';
			           result = result + '</table>';
			           result = result + '</div>';
			           result = result + '</div>';
			           result = result + '<div id="choicepopUp"></div>';
	  				
	  				$("#uploadXlsHistoryDiv").empty();
	    			$("#uploadXlsHistoryDiv").append(result);
	    			$("#uploadXlsHistoryDivTitle").removeAttr('hidden');
	        	
	        	
			 },
	        error: function(e){
	        }
	    });
		
	}
		
	this.deleteFailureRecords = function(documentKey,entityType,dateFormat){
		xlsupload.alertMessage(documentKey,entityType,dateFormat);
	}	
	
	
	this.alertMessage = function(documentKey,entityType,dateFormat){
	    var result = "";
		result = result +'	<div class="project_inn_mask"></div>';
		result = result +'	   <div id="remove_user_pop" class="remove_user_pop remove_user_pop_a">';
		result = result +'		<p>Are you sure to delete Failure History?</p>';
		result = result +'		<div>&nbsp;&nbsp;&nbsp;</div>';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="xlsupload.deleteRecord(\''+documentKey+'\',\''+entityType+'\',\''+dateFormat+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="xlsupload.closePopUp();"></b>';
		result = result +'	</div>';
		$("#choicepopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);

	}
	
	this.closePopUp = function(){
		$("#choicepopUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
	
	this.deleteRecord = function(documentKey,entityType,dateFormat){
		xlsupload.closePopUp();
		$("#failure"+documentKey).html("<td colspan='8'  id='loading"+documentKey+"'></td>");
		$("#loading"+documentKey).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/deleteBulkImportFailureRecordsByFailureDocumentKey?documentKey="+documentKey, 
	        success: function(result) {
	        	if(result && result === "Success") {
	        		xlsupload.getFailureUploadStats(entityType,dateFormat);
	        	}
	        	
	        },
	        error: function(e){
	        }
	    });
	}
	
}