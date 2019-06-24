



<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
<div id="projectPopupDetailsLoading"></div>
				<h5 class="headerAdsrch" id="ProjectDetailsDiv">Project Details
					<a href="javascript:void(0)" class="close-div" onClick="closeProjectDetailsPopup()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
							
						
						<div class="col-md-4">
						<div class="row">
						   <div id="projectDetailsDiv"></div>
						</div>
						</div>
						
						
					</div>
				</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Close" id="search_requiremet"
								onclick="closeProjectDetailsPopup();" class="btn btn-sm btn-success" />
						</div>
					</div>
				</div>


<script>

function closeProjectDetailsPopup(){
	$("#projectDetailsDiv").empty();
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").fadeOut();
}

function openProjectDetailsPopup(projectUUID,orgLocationId){
	
	$("#projectDetailsDiv").empty();
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#projectPopupDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProjectPopDetails?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&orgLocationId="+orgLocationId, 
	        success: function(result) {
	        	
	        	var result = '';
	        	
	        	result = result +'<div class="clearfix mb">';
	        	result = result +'<label class="col-md-4"><strong>Project Name :</strong></label>';
	        	result = result + '<div class="col-md-8 title="'+result.projectName+'">'+result.projectName+'</div><div class="clearfix"></div>';
                result = result +'</div> ';
                
                $("#projectDetailsDiv").append(result);
	        }
	        
	 });
	
	
	 $("div.cand-advsrch-popup").toggle();
	 $(".advancedsearch-mask").fadeIn();
	 $("#projectPopupDetailsLoading").removeAttr("style"); 
}
</script>