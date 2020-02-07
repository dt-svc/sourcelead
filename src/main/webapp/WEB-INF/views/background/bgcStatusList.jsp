	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <label class="col-md-4 mt10">Status</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle width100percnt" type="button" id="menu1" data-toggle="dropdown">-- Select Status --
              <span class="caret mt10 pull-right"></span></button>
              <ul class="dropdown-menu width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn" >
              <c:forEach var="status" items="${statusList}" varStatus="rows">
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="status${status.id}" class="checkbox-custom" name="status${status.id}" type="checkbox">
                    <label for="status${status.id}" class="checkbox-custom-label"><span class="ml5">${status.statusName}</span></label>
                  </div>
                </li>
                </c:forEach>
                <li role="Address">
                  <a href="${SourceLeadWeb_vmsUrl}/bgc/bgcOrgStatusList" target="_blank" class="text-primary">
                    <i class="fa fa-plus paddingRight mr10"></i>Add Status
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-1 text-center padding_left">
              <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgStatusList();"></a>
          </div>
          </div>
        </div>
     
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     
     function refreshOrgStatusList(){
    	 $.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/refreshBgcStatusList?",
				success : function(reponse) {
					$("#orgStatusDiv").html(reponse);
				},
			});
     }
     
     </script>