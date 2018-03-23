<div id="subscription" class="tab-pane fade">
<div class="project_tabs_inn_table vendor_wrap_inn_table mt">
<a href="javascript:void(0)" class="addcon-pop btn btn-sm btn-primary mb10" onclick="addSubscription();"><i class="glyphicon glyphicon-plus"></i>&nbsp;Subscribe</a>
<div id="subscriptionList"></div>
<div id="subscriptionListLoading"></div>
</div>
</div>


<script>
function orgSubscriptionList(){
	  var sourceleadUrl ='${sourceleadUrl}';
      var orgId = '${orgId}';
      
	 $("#subscriptionListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		  type : "POST",
		  url: sourceleadUrl+"/getOrgSubscribedList?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId,      
           success :function(json) {
	                	 var result = "";
	                	 result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	                     result = result +'<th>Subscription Type</th>';
	               		 result = result +'<th>Country</th>';
	               		 result = result +'<th>Invoice Amount</th>';
	               		 result = result +'<th>From Date</th>';
	               		 result = result +'<th>To Date</th>';
	               		 result = result +'<th>Storage</th>';
	               		 result = result +'<th>Modules</th>';
		                 result = result +"</tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	               
	                    	 result = result +'<tr>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.subscriptionType+'>'+opt.subscriptionType+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.country+'>'+opt.country+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.currency+' '+opt.invoiceAmount+'>'+opt.currency+' '+opt.invoiceAmount+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.fromDateStr+'>'+opt.fromDateStr+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.toDateStr+'>'+opt.toDateStr+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+opt.storageType+'>'+opt.storageType+'</div></td>';
	                    	 
	                 	    result = result +'<td>';
		   		        	result = result +'<div class="positionRelative">';
			   		         for(var k=0; k<2; k++){
			   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.modulesList[k]+'</p>';
			   		         }
		   		    	
			   		        result = result +'<div class="show_Empdetails showjoblist">';
		   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
	
		   		        	result = result +'<div class="emailvalues emailvaluesNew">';
		   		        	
 							for(var k=0; k<opt.modulesList.length; k++){
 								result = result +'<p><i class="fa fa-check-square-o"></i>'+opt.modulesList[k]+'</p>';
 			   		        	
		   		    	 	}
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   

	   		        	result = result +'</div>';
	   		        	result = result +'</td>';
	                    	 
	                    	
	                    	 result = result +'</tr>';
	                    	
                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#subscriptionList").empty();
	                     $("#subscriptionList").append(result); 
	                     $("#subscriptionListLoading").removeAttr("style");
         },
           
      }); 
	 
}
</script>