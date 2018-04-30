  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateBankData(userId){  
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {

            	var result = "";
            	result = result + '<div class="col-md-12">';
            	result = result + '<a href="us-associates.html" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
            	result = result + '</div>';
            	result = result + ' <div class="user_details_cta user_detailsCta">';
            	result = result + '<div class="project_details_cta"> <strong>Account Type:</strong> <span>';
            	result = result + ' <input type="text" value='+json.accountType+' placeholder="Project name" class="project_detfield">';
            	result = result + ' </span>';
            	result = result + '  <div class="clearfix"></div>';
            	result = result + ' </div>';
            	result = result + ' <div class="project_details_cta"> <strong>Bank Name:</strong> <span>';
            	result = result + '   <input type="text" value='+json.bankName+' placeholder="Kotak Mahendra Bank" class="project_detfield">';
                result = result + '    </span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Account Number:</strong> <span>';
                result = result + '<input type="text" value='+json.accountNumber+' placeholder="854XXXX222" class="project_detfield">';
                result = result + '</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Routing Number:</strong> <span>';
                result = result + '<input type="text" value='+json.routingNumber+' placeholder="044072324" class="project_detfield">';
                result = result + '</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '</div>';
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-21").append(result); 
          },
            
       }); 
  } 
</script>
 
 
 
<!--  <div id="tab-2" class="tabsMenu_tabs_inn clearfix"> -->
		
		<!--   </div> -->