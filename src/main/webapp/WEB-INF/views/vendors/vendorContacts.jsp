<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script type="text/javascript">
window.onload = function () {
	cscInit();
 };
</script>
<script type="text/javascript">
	function clientContactList(orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	var result = "";
	        	
	        	var selected1 = '';
				var selected2 = '';
				var selected3 = '';
				var selected4 = '';
				var selected5 = '';
				
					
	        	result = result + '    	<div class="project_tabs_inn_head">';
	    		result = result + '		<input class="vendorfild" type="text"><i class="fa fa-search"></i> | <a href="javascript:void(0)" class="addcon-pop">';
	    		result = result + '		<i class="glyphicon glyphicon-plus"></i>Add Contact</a>';
	    		result = result + '		</div>';
	    		
	    		result = result + '        <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		    	    result = result + '	<table>';
		    		result = result + '	<tbody>';
		    		result = result + '		<tr>';
		            result = result + '	 	<th>Name</th>';
		            result = result + '	     <th>Email</th>';
		            result = result + '	     <th>Ph. No</th>';
		            result = result + '	     <th>Owner</th>';
		            result = result + '	     <th>Status</th>';
		            result = result + '	     <th>Invitation status</th>';
		            result = result + '	     <th>Action</th>';
		            result = result + '	 </tr>';
		            
			          for (var i=0; i<json.length; i++)
					  {        
				        		var opt = json[i];
				        		
				        		if(opt.spocStatus == 'Active'){
									selected1 = 'selected';
								} 
							
								if(opt.spocStatus == 'In-Active'){
										selected2 = 'selected';
									} 
								
								if(opt.spocStatus == 'New'){
										selected3 = 'selected';
									}
								
								if(opt.spocStatus == 'Negotiation'){
										selected4 = 'selected';
									} 
								
								if(opt.spocStatus == 'Contracted'){
									selected5 = 'selected';
								} 
				        		
								var spocMobilePhone;
			   		    		if(opt.spocMobilePhone != null && opt.spocMobilePhone != '' && opt.spocMobilePhone !='undefined'){
			   		    			 spocMobilePhone = opt.spocMobilePhone;
			   		    		}else{
			   		    			spocMobilePhone = "---";
			   		    		}
				        		
				        		result = result + '	 <tr>';
				                result = result + '	 	<td><div>'+opt.spocName+'</a></div></td>';
				                result = result + '	     <td><div>'+opt.spocEmail+'</div></td>';
				                result = result + '	     <td><div>'+spocMobilePhone+'</div></td>';
				                result = result + '	     <td><div>'+opt.ownerName+'</div></td>';
				                
				                result = result + ' <td><div>';
								result = result + ' <select id="spocStatus" name="spocStatus">';
								result = result + ' <option value="">-Select Status-</option>';
								result = result + ' <option '+selected1+'>Active</option>';
								result = result + ' <option '+selected2+'>In-Active</option>';
								result = result + ' <option '+selected3+'>New</option>';
								result = result + ' <option '+selected4+'>Negotiation</option>';
								result = result + ' <option '+selected5+'>Contracted</option>';
								result = result + ' </select>';
								result = result + ' </div></td>';
				                
				                result = result +'<td data-title="Invitation Status"><div>';
				                if(opt.invitationStatus == 'Accepted'){
				  	    			  result = result +'<b class="proj_active">'+opt.invitationStatus+'</b>';
			  	    			}
			   		    		if(opt.invitationStatus == 'Rejected'){
			  	    			  result = result +'<b class="proj_deactivate">'+opt.invitationStatus+'</b>';
			  	    			}
			   		    		if(opt.invitationStatus == 'NEW'){
				  	    			  result = result +'<b class="proj_process">'+opt.invitationStatus+'</b>';
			  	    			}
			   		    		if(opt.invitationStatus == 'Sent'){
			  	    			  result = result +'<b class="proj_process">'+opt.invitationStatus+'</b>';
			  	    			}
			   		    		if(opt.invitationStatus == 'Resend'){
				  	    			  result = result +'<b class="proj_process">'+opt.invitationStatus+'</b>';
				  	    			}
			   		    		result = result +'</div></td>';
				                
				                if(json[i].status != null && json[i].status != '' && json[i].status !='undefined' && json[i].status != "INACTIVE"){
				                result = result + '	    <td><div>';
				                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="deleteSpoc('+json[i].id+'\,\''+orgRelationID+'\')";></i>';
				                	                    result = result +'</div></td>';
			                     }
			                     else{
			                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
			                     }
				                result = result + '	 </tr>';
						} 
			        	result = result + '	     </tbody>';
			    	    result = result + '	 </table>   ';
	    		result = result + '	     </div>';
	    		$("#tab-2").empty();  
	    	    $("#tab-2").append(result);  
	    	    $("#tab-2").show();
	    		$(".project-tabs-menu a").click(function(event) {
	    		     event.preventDefault();
	    		     $(this).parent().addClass("current");
	    		     $(this).parent().siblings().removeClass("current");
	    		     var tab = $(this).attr("href");
	    		     $(".project_tabs_inn").not(tab).css("display", "none");
	    		     $(tab).fadeIn();
	    		 });
	        },
	    });
	}
</script>
<!--  client details ends -->
