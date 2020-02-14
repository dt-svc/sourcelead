	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <label class="col-md-4 mt10">Status</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown" >
              <button class="btn btn-default dropdown-toggle width100percnt tbldata-dotdot-200" type="button" id="statusList" data-toggle="dropdown">-- Select Status --
              <span class="caret mt10 pull-right"></span></button>
              <ul class="dropdown-menu width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn" >
              <c:forEach var="status" items="${statusList}" varStatus="rows">
                <li >
                  <div class="checkbox-new clearfix ml mt5">
                    <c:choose>
                    <c:when test="${not empty selStatusList && fn:contains(selStatusList, status.id)}">
                    <input id="status${status.id}" class="checkbox-custom statusGroup" statusId ="${status.id}" onclick="pushStatusIds();" name="statusString" checked type="checkbox" data-validation="required"  >
                    <label for="status${status.id}" class="checkbox-custom-label"><span class="ml5">${status.statusName}</span></label>
                  </div>
                 </c:when>
                   <c:otherwise>
                    <input id="status${status.id}" class="checkbox-custom statusGroup" statusId ="${status.id}" onclick="pushStatusIds();" name="statusString"  type="checkbox" data-validation="required"  >
                    <label for="status${status.id}" class="checkbox-custom-label"><span class="ml5">${status.statusName}</span></label>
                   </c:otherwise>
                  </c:choose>
                </li>
                </c:forEach>
                <li role="Address">
                  <a href="${SourceLeadWeb_vmsUrl}/bgc/bgcOrgStatusList" target="_blank" class="text-primary">
                    <i class="fa fa-plus paddingRight mr10"></i>Add Status
                  </a>
                </li>
              </ul>
            </div>
            <p id="statusValid" style="color: red; display:none">Status is required</p>
          </div>
          <div class="col-md-1 text-center padding_left">
              <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgStatusList();"></a>
          </div>
          </div>
        </div>
     
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     var statusObj= [];
     var statusNames=[];
     function refreshOrgStatusList(){
    	 $.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/refreshBgcStatusList?",
				success : function(reponse) {
					$("#orgStatusDiv").html(reponse);
				},
			});
     }
     function pushStatusIds() {
    	 statusObj= [];
    	 statusNames = [];
    	 //statusObj.toString();
    	 $('.statusGroup').each(function(index, value){
        	 if ($(value).is(":checked")){
        		 statusObj.push($(value).attr("statusId"));
        		 statusNames.push($(value).closest("li").text());
             }
         });
    	 if(statusObj.length==0){
    		 $('#statusValid').show();
    		 $('#statusList').text("-- Select Status --");
    		 return false;
    	 }else{
    		 $('#statusValid').hide();
    		 $('#statusList').text("");
    		 $('#statusList').text(statusNames.join(','));
    		 return true;
    	 }
    	 
     }
    
     </script>
     <script>
     $(document).ready(function() {
    	 <c:if test="${not empty selStatusList}">
    	 pushStatusIds();
    	 </c:if>
     })
     </script>