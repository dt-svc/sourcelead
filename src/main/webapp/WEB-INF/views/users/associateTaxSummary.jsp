  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateTaxSummary(userId){  
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateTaxSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            var result = "";
       		result = result + '<div class="col-md-12">';
       		result = result + ' <a href="us-associates.html" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
       		result = result + '</div>';
       		result = result + ' <div class="user_details_cta user_detailsCta userDetails-cta">';
       		result = result + ' <h4>Federal Tax</h4>';
       		result = result + ' <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '  <input type="text" value='+json.federaltaxFillingStatus+' placeholder="Project name" class="project_detfield">';
       		result = result + '  </span>';
       		result = result + '   <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Allowances:</strong> <span>';
       		result = result + '     <input type="text" value='+json.federaltaxAllowances+' placeholder="First name" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '      <input type="text" value='+json.federaltaxAdditionalWithHolding+' placeholder="Client" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '    </div>';
                         
       		result = result + '     <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '      <h4>State Tax</h4>';
       		result = result + '    <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '     <input type="text" value='+json.stateTaxFillingStatus+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Allowances:</strong>';
       		result = result + '    <span class="fix">';
       		result = result + '      <input type="text" value='+json.stateTaxAllowances+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '        </span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '     <input type="text" value='+json.stateTaxAdditionalWithHolding+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '    </span>';
       		result = result + '    <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + ' </div>';
       		result = result + '   <div class="user_details_cta user_detailsCta userDetails-cta">';
                        
       		result = result + '    <h4>Residence Location</h4>';
                         
       		result = result + '   <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '     <input type="text" value='+json.residenceLocationFillingStatus+' placeholder="Project name" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Allowances:</strong> <span>';
       		result = result + '       <input type="text" value='+json.residenceLocationAllowances+' placeholder="First name" class="project_detfield">';
       		result = result + '         </span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '      </div>';
       		result = result + '      <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '        <input type="text" value='+json.residenceLocationAdditionalWithHolding+' placeholder="Client" class="project_detfield">';
       		result = result + '        </span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     </div>';
       				
       		result = result + ' <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '   <h4>Additional</h4>';
       		result = result + '     <div class="project_details_cta"> <strong>Federal:</strong><span>'+json.federalExempt+'</span>';
       		result = result + '       <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Mediacare:</strong>';
       		result = result + '      <span>'+json.medicareExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '       <div class="project_details_cta"> <strong>Socail Security:</strong>';
       		result = result + '        <span>'+json.socailSecurityExempt+'</span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>FUTA:</strong>';
       		result = result + '      <span>'+json.futaExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>SUTA:</strong>';
       		result = result + '     <span>'+json.sutaExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '      </div>';
                         
       		result = result + '    </div>';
       		result = result + ' </div>';
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-22").empty(); 
                $("#tab-22").append(result); 
          },
            
       }); 
  } 
</script>
 
 
 
<!--  <div id="tab-2" class="tabsMenu_tabs_inn clearfix"> -->
		
		<!--   </div> -->