<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

       <!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
                          <div class="form-horizontal clearfix">
                                                    
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="First name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Last name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Email Id *">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Work phone">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Owner">
                              </div>
                            </div>                                                                                                                
                            <div class="clearfix mb10">
                              <div class="col-md-12 checkbox">
                                <label>
                                <input type="checkbox" name="Position" id="endDate">Send Invitation</label>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </div>
                        </div>
				<!-- Popup Div -->  
				
<div id="popUp"></div>				

<!-- client details View starts-->
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
	    		if(json.length != null && json.length != ''){
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
								result = result + ' <select id="spocStatus" name="spocStatus" onChange="changeSpocStatus('+json[i].id+'\);">';
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
				                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="terminateSpoc('+json[i].id+'\,\''+orgRelationID+'\')";></i>';
				                	                    result = result +'</div></td>';
			                     }
			                     else{
			                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
			                     }
				                result = result + '	 </tr>';
						} 
			        	result = result + '	     </tbody>';
			    	    result = result + '	 </table>   ';
			    	    
			    	    result = result + '<div class="project_inn_mask"></div>';
					    result = result + '<div class="project_error">';
					    result = result + '	<p>User delete message here ....</p>';
					    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
					    result = result + '</div>';
			    	    
	        }else{
   			 result = result + '<div class="table_nodata">';
   			 result = result + '<div class="table_nodata_inn">';
   			 result = result + '<img src="resources/images/no-data.png" />';
   			 result = result + '<span>Nothing is here to show </b>';
   			 result = result + '</span>';
   			 result = result + '<div class="clearfix"></div>';
   			 result = result + '</div>';
   			 result = result + '</div>';
   		 }
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


<script>
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	


	// Levels adding script
	
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });	 


</script> 

<script>
function terminateSpoc(spocId, orgRelationID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReason(spocId, orgRelationID){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	if(reason){
		$("#reasonForSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
		    	clientContactList(orgRelationID);
		    }
	   });
	}else{
		$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	  
}
</script>

<script>
function cancelTermination(){
$("#popUp").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function changeSpocStatus(spocId){
	var spocStatus = document.getElementById("spocStatus").value;
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	clientContactList(orgRelationID);
	    }
   });
}


</script>