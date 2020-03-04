	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <div class="clearfix">
            <label class="col-md-4 mt10">Status Type<span class="colorRed ml5">*</label>
            <div class="col-md-6 padding_right">
            <select multiple class="chosen-select" id="statusString" name="statusString" data-placeholder="Select Status" field-name="Status Type"  data-validation="required">
             <c:forEach var="status" items="${statusList}" varStatus="rows">
             <c:choose>
                    <c:when test="${not empty selStatusList && fn:contains(selStatusList, status.id)}">
                    <option selected value="${status.id}">${status.statusName}</option>
                    </c:when>
                   <c:otherwise>
                   <option  value="${status.id}">${status.statusName}</option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
            </select>
          </div>
          <div class="col-md-2 text-center padding_left">
          	 <a title="Add Status" href="${SourceLeadWeb_vmsUrl}/bgc/bgcOrgStatusList" target="_blank" class="text-primary">
                    <i class="fa fa-plus btn btn-primary newRefreshbtn"></i>
             </a>
	         <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgStatusList();"></a>
          </div>
        </div>
     
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     var templateUuid ='${bgcTemplateDto.uuid}';
     function refreshOrgStatusList(){
    	 $.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/refreshBgcStatusList?templateUuid="+templateUuid,
				success : function(reponse) {
					$("#orgStatusDiv").html(reponse);
					$("#statusString").chosen();
				},
			});
     }
     
     
     </script>
     