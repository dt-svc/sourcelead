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
    <form name = "availableAssociates" id = "availableAssociatesFormId">
      <div class="clearfix minHeight mt">
        <div class="clearfix">
          <div class="col-md-5">
            <div class="form-group row">
              <label class="col-md-12 paddingRight">Associate Name</label>
              <div class="col-md-12">
              
                 <select class="chosen-select form-control" multiple data-placeholder="Select Associate" name="userIds" id="associatesId" onchange ="getProjects();">
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
                <div class="col-md-9">
                      <select class="chosen-select form-control" multiple data-placeholder="Select Project" name="projectsIds" id="projects" field-name="Projects" data-validation="required">
                        <option value="">-Select Projects-</option>
                      </select>
                  </div>
            </div>
          </div>
           <div class="clearfix"></div>
          <div class="col-md-2">
            <div class="row">
              <label class="col-md-12 paddingRight">From</label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" name = "fromDate" id="fromDate" data-validation="required">
              </div>
            </div>
          </div>
          <div class="col-md-2">
            <div class="row">
              <label class="col-md-12 paddingRight">To</label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" name = "toDate" id="toDate" data-validation="required">
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix footerstyelsNew">
          <div class="col-lg-12">
            <div class="pull-right">
              <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="prepareReport();">
              <input value="Reset" class="btn btn-defualt ml10" type="button" onclick ="reset();">
            </div>
          </div>
        </div>
      </div>
      </form>
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
			success : function(response) {
				 $("#projects").empty();
				 
				 var projects = "";
				 projects +=  "<option value=''>-- Select Project --</option>"; 
				 for(var e in response.data) {
					 projects += "<option value="+response.data[e].id+">"+response.data[e].projectName+"</option>";
				 }
		        $("#projects").append(projects);
		        $('#projects').trigger("chosen:updated"); 
			}
		})
      }
      </script>
      
      <script>
      $("#fromDate").datepicker();
      $("#toDate").datepicker();
      </script>
      
      <script>
      function reset(){
      	$('#fromDate').val('');
    	$('#toDate').val('');
    	$('#associatesId').val('');
    	$('#projects').val('');
    	$('#associatesId').trigger("chosen:updated");
    	$('#projects').trigger("chosen:updated");
      }
      </script>
      
      <script>
      function prepareReport(){
    	  alert("inside")
    	  var associates =$("#associatesId").val();
    	  var projects = $("#projects").val();
    	  var fromDate = $('#fromDate').val();
  		  var toDate = $('#toDate').val();
    	       if(associates == null || associates == ''|| associates == 'undefined'){
    		  associates = '';
    	            }
    	       if(projects == null || projects == ''|| projects == 'undefined'){
    		  projects = '';
    	             }
    		  if ($('#availableAssociatesFormId').validate(false, validationSettings)){
    			  $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
    			  var formData = getFormData1("availableAssociatesFormId");
    			  $.ajax({
    				  url: sourceleadUrl+"/prepareReport",
  		            type: "POST",
  		            data: formData,
  	 		        processData: false,
  	 		        contentType:false, 
  		            success: function (data) { 
  		            	
  		            }
    			  })
    		           
    	  
      }else{
    	  return false;
        }
      
     }
      
      
      
      
      
      </script>
      
      
      
      
      
      <jsp:include page="../masterFooter.jsp" />