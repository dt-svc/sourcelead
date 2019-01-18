  <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  
  
  
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Available Associates</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Reports &raquo; Available Associates</div>
    </section>
    <div class="content-area content-new clearfix backgroundWhite">
      <div class="clearfix minHeight mt">
        <div class="clearfix">
          <div class="col-md-5">
            <div class="form-group row">
              <label class="col-md-12 paddingRight">Associate Name</label>
              <div class="col-md-12">
              
                 <select class="chosen-select form-control" multiple data-placeholder="Select Associate" name="associates" id="associatesId" onchange ="getProjects();">
			            <c:forEach var="option" items="${userList}">
							<option value="${option.id}">${option.fullName}</option>
						</c:forEach>
			          </select>
              
              </div>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group row">
              <label class="col-md-12 paddingRight">Project Name</label>
              <div class="col-md-12">
                <select class="chosen-select" data-placeholder="Select Categories" multiple>
                  <option>Project1</option>
                  <option>Project2</option>
                </select>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="col-md-2">
            <div class="row">
              <label class="col-md-12 paddingRight">From</label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" id="fromDate">
              </div>
            </div>
          </div>
          <div class="col-md-2">
            <div class="row">
              <label class="col-md-12 paddingRight">To</label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" id="toDate">
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix footerstyelsNew">
          <div class="col-lg-12">
            <div class="pull-right">
              <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runPayrollregister();">
              <input value="Reset" class="btn btn-defualt ml10" type="button">
            </div>
          </div>
        </div>
      </div>
        </div>
      </section>
      
      <script>
      function getProjects(){
    	var associatesId = [];
    	var associatesId = $("#associatesId").val();
		if(associatesId != null && associatesId!="" && associatesId!= "undefined"){
			associatesId = associatesId;
		}
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/getProjectsList?&associatesId="+associatesId,
			success : function(result) {
			
			}
			
		})
		
		
		
		
		
		
      }
      
      
      
      
      </script>
      
      
      
      
      
      
      
      
      
      
      
      <jsp:include page="../masterFooter.jsp" />