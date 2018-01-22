<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
   
    <script type="text/javascript">
    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
 </script>
 
   <form role="form"  id="projectFolderPermission_form" name="projectFolderPermission_form">
	<div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" onclick="permissionClose();">&times;</button>
      <c:if test="${! empty folderId && empty documentId}">
      <h4 class="modal-title">Folder Permissions</h4>
      </c:if>
      
       <c:if test="${! empty documentId}">
      <h4 class="modal-title">Document Permissions</h4>
      </c:if>
      
    </div>
    <div class="modal-body">
    <div class="row">
        <div class="col-md-6">
          <label><strong>Roles Based</strong></label>
		          <div class="panel panel-default">
			          <div class="panel-body">
				          <div class="row minHeight40 mb">
				          	<label class="col-md-3">View</label>
				          	<div class="col-md-9 dropdown-select">
				          		<select class="form-control chosen-select choose" multiple  id="readRoleIds" name="readRoleIds" data-placeholder="Select read Roles">
				          		 <c:forEach var="roles" items="${memberRolesList}">
				          		      <c:set var = "NUM" value = "${0}"/>
						          		 <c:forEach var="existedRole" items="${readList}">
										    <c:if test="${existedRole.userRoleId.id eq roles.key}">
										    	<c:set var = "NUM" value = "${1}"/>
										       <option  selected value="${roles.key}">${roles.value}</option>
										    </c:if>
										</c:forEach>
										<c:if test="${NUM == 0}">
										<option value='${roles.key}'>${roles.value}</option>
										</c:if>
				          		 </c:forEach>
						        </select>
				          	</div>
				          </div>
				          <div class="row minHeight40 mb">
				          	<label class="col-md-3">Owner</label>
				          	<div class="col-md-9 dropdown-select">
				               <select class="form-control chosen-select choose" multiple  id="writeRoleIds" name="writeRoleIds" data-placeholder="Select write Roles">
				                 <c:forEach var="roles" items="${memberRolesList}">
				          		     <c:set var = "NUM" value = "${0}"/>
						          		 <c:forEach var="existedRole" items="${writeList}">
										    <c:if test="${existedRole.userRoleId.id eq roles.key}">
										    	<c:set var = "NUM" value = "${1}"/>
										       <option  selected value="${roles.key}">${roles.value}</option>
										    </c:if>
										</c:forEach>
										<c:if test="${NUM == 0}">
										<option value='${roles.key}'>${roles.value}</option>
										</c:if>
				          		  </c:forEach>
						        </select>
				          	</div>
				          </div>
			          </div>
		          </div>
           </div>
		   <div class="col-md-6">
			<label><strong>Member Based</strong></label>
				<div class="panel panel-default">
					<div class="panel-body">
					<div class="row minHeight40 mb">
						<label class="col-md-3">View</label>
						<div class="col-md-9 dropdown-select-chosen">
							<select class="form-control chosen-select choose" multiple  id="readOtherMemberIds" name="readOtherMemberIds" data-placeholder="Select Read Project Members" >
					             <c:forEach var="employee" items="${projectMembersList}">
						             <c:set var = "NUM" value = "${0}"/>
							             <c:forEach var="existedMember" items="${readList}">
											    <c:if test="${existedMember.projectMemberId.id eq employee.id}">
											    	<c:set var = "NUM" value = "${1}"/>
											       <option  selected value="${employee.id}">${employee.empName}</option>
											    </c:if>
											</c:forEach>
										<c:if test="${NUM == 0}">
										    <option value='${employee.id}'>${employee.empName}</option>
										 </c:if>
				          		 </c:forEach>
					       </select>
						</div>
					</div>
					<div class="row minHeight40 mb">
						<label class="col-md-3">Owner</label>
						<div class="col-md-9 dropdown-select-chosen">
					     <select class="form-control chosen-select choose" multiple  id="writeOtherMemberIds" name="writeOtherMemberIds" data-placeholder="Select Write Project Members" >
					           <c:forEach var="employee" items="${projectMembersList}">
					           			<c:set var = "NUM" value = "${0}"/>
						          		 <c:forEach var="existedMember" items="${writeList}">
											    <c:if test="${existedMember.projectMemberId.id eq employee.id}">
											    	<c:set var = "NUM" value = "${1}"/>
											       <option  selected value="${employee.id}">${employee.empName}</option>
											    </c:if>
											</c:forEach>
										<c:if test="${NUM == 0}">
										    <option value='${employee.id}'>${employee.empName}</option>
										 </c:if>
				          		 </c:forEach>
					     </select>
						</div>
					</div>
					</div>
				</div>
		</div>
      </div>
    </div>
    <div class="modal-footer">
     <button type="button" class="btn btn-primary mr10" onclick="saveFolderNamePermission(${folderId},${documentId});">Apply</button>
      <button type="button" class="btn btn-default" onclick="permissionClose();">Cancel</button>
    </div>
  </div>
  </form>
  
 