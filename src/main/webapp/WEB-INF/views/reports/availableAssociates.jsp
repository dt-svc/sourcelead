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
        <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard">Reports Dashboard</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/associateReportsList">Associate Reports</a> &raquo; Available Associates Report  </div>
    </section>
    <div class="content-area content-new clearfix backgroundWhite">
    <form name = "availableAssociates" id = "availableAssociatesFormId">
      <div class="clearfix minHeight mt">
        <div class="clearfix">
          <div class="col-md-5">
            <div class="form-group row" id="associatesIdDiv">
              <label class="col-md-12 paddingRight">Associate Name</label>
              <div class="col-md-12">
              <select class="chosen-select form-control" multiple data-placeholder="Select Associate" autocomplete="off" onchange="getProjects();" id="associatesId" name="userIds" field-name="Associate" >
			            <c:forEach var="option" items="${empList}">
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
                      <select class="chosen-select form-control" multiple data-placeholder="Select Project"  autocomplete="off" name="projectsIds" id="projects" field-name="Projects" ">
                      </select>
                  </div>
            </div>
          </div>
           <div class="clearfix"></div>
         <div class="col-md-2">
                        <div class="row" id="fromDateDiv">
                            <label class="col-md-12 paddingRight">From <span class="text-danger">*</span></label>
                            <div class="col-md-12">
                                <input type="text" class="form-control calendar" name="fromDate"
                                    id="fromDate" field-name="From Date"  data-validation="required"
                                    readonly>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="row" id="toDateDiv">
                            <label class="col-md-12 paddingRight">To <span class="text-danger">*</span></label>
                            <div class="col-md-12">
                                <input type="text" class="form-control calendar" name="toDate"
                                    id="toDate" field-name="To Date"  data-validation="required"
                                    readonly>
                            </div>
                        </div>
                    </div>
        </div>
        <div class="clearfix footerstyelsNew">
          <div class="col-lg-12">
            <div class="pull-right">
              <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="prepareReport();">
              <input value="Reset" class="btn btn-defualt ml10" type="button" onClick ="resetForm();">
            </div>
          </div>
        </div>
      </div>
      </form>
        
        
        <div class="clearfix" id="avlbAssociatesReporttableDiv" style="display: none;">
          
          <div class="positionRelative reportHead clearfix"> 
            <ul class="pull-right list-unstyled list-inline">
                 <li><a href="javascript:void(0)" class="overIcon" id="exportOptions" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
							<a href="javascript:void(0)" onclick="reportExport('Excel');" title="Excel">Export Excel</a>            
						</span>
					</div>
				</li> 
            </ul>
          </div>
	          <div class="tableNoborder clerfix">
	               <div id="availableAssociatesReporttableList"></div>
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
			success : function(response) {
				 $("#projects").empty();
				 
				 var projects = "";
				 for(var e in response.data) {
					 projects += "<option value="+response.data[e].id+">"+response.data[e].projectName+"</option>";
				 }
		        $("#projects").append(projects);
		        $('#projects').trigger("chosen:updated"); 
			}
		})
      }
      </script>
      
<!--       <script>
      $("#fromDate").datepicker();
      $("#toDate").datepicker();
      </script> -->
      
      
      
      <script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		$('#fromDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose: function (selected) {
	            var dt = $(this).datepicker('getDate', '+1d');
	            $("#toDate").datepicker("option", "minDate", dt);
				var event = arguments.callee.caller.caller.arguments[0];
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
		
		});
		
		$('#toDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose: function (selected) {
	            var dt = $(this).datepicker('getDate', '-1d');
	            $("#fromDate").datepicker("option", "maxDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
		});
		
	});
</script>
      
      <script>
      function resetForm(){
        $('#associatesIdDiv').find('p.jquery_form_error_message').remove();
    	$('#fromDateDiv').find('p.jquery_form_error_message').remove();
    	$('#toDateDiv').find('p.jquery_form_error_message').remove();
      	$('#fromDate').val('');
    	$('#toDate').val('');
    	$('#associatesId').val('');
    	$('#projects').val('');
    	$('#associatesId').trigger("chosen:updated");
    	$('#projects').trigger("chosen:updated");
    	document.getElementById("avlbAssociatesReporttableDiv").style.display="none";
      }
      </script>
      
     <script>
    function prepareReport() {
        var associates = $("#associatesId").val();
        var projects = $("#projects").val();
        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        if (associates == null || associates == '' || associates == 'undefined') {
            associates = '';
        }
        if (projects == null || projects == '' || projects == 'undefined') {
            projects = '';
        }
        if ($('#availableAssociatesFormId').validate(false, validationSettings)) {
            $("#loadingId")
                    .attr(
                            'style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
           var formData = getFormData1("availableAssociatesFormId");
                            $.ajax({
                                        url : sourceleadUrl + "/prepareReport",
                        type : "POST",
                        data : formData,
                        processData : false,
                        contentType : false,
                        success : function(result) {
                            document
                                    .getElementById("avlbAssociatesReporttableDiv").style.display = "block";
                            $("#availableAssociatesReporttableList").show();
                            if (result && result.response === "SUCCESS"
                                    && result.object != null
                                    && result.object != "") {
                                var text = "";
                                text += "<table class='table mb0'>";
                                text += " <thead>";
                                text += " <tr>";
                                text += " <th><div class='width180px text-center'>Project Name</div></th>";
                                text += " <th><div class='width180px text-center'>Project Duration</div></th>";
                                text += " <th><div class='width180px text-center'>Associate Duration</div></th>";
                                text += " <th><div class='width180px text-center'>Availability %</div></th>";
                                text += " <th><div class='width180px text-center'>No. of Hours</div></th>";
                                text += " </tr>";
                                text += " </thead>";
                                text += " <tbody>";

                                for ( var e in result.object) {
                                    text += "<tr>";
                                    text += " <td colspan='5' class='noPad'>";
                                    text += " <table class='table mb0'>";
                                    text += " <tbody>";
                                    text += " <tr>";
                                    text += " <td colspan='5'><div class='text-left textBold text-muted'>"
                                            + e.split("<<>>")[1]
                                            + " </div></td>";
                                    text += " </tr>";
                                    var reportList = result.object[e];
                                    var totalavailable = 0, totalHours = 0, orgWorkingHours = 0;
                                    for ( var e1 in reportList) {
                                        var report = reportList[e1];
                                        text += " <tr>";
                                        text += " <td><div class='width180px text-center'>"
                                                + (report.projectName !== null ? report.projectName
                                                        : "--")
                                                + " </div></td>";
                                        text += " <td><div class='width180px text-center'>"
                                                + (report.projectStartDate !== null ? report.projectStartDate
                                                        : "--")
                                                + " To "
                                                + (report.projectEndDate !== null ? report.projectEndDate
                                                        : "--")
                                                + " </div></td>";
                                        text += " <td><div class='width180px text-center'>"
                                                + (report.empStartDate !== null ? report.empStartDate
                                                        : "--")
                                                + " To "
                                                + (report.empEndDate !== null ? report.empEndDate
                                                        : "--")
                                                + " </div></td>";
                                        text += " <td><div class='width180px'> "
                                                + (report.availableHours !== null ? report.availableHours
                                                        : "--")
                                                + " </div></td>";
                                        text += " <td><div class='width180px'>"
                                                + (report.hoursperWeek !== null ? parseFloat(
                                                        report.hoursperWeek)
                                                        .toFixed(2)
                                                        : "----")
                                                + "</div></td>";
                                        text += " </tr>";

                                        totalavailable += (report.availableHours !== null && report.availableHours !== "") ? parseFloat(report.availableHours)
                                                : 0;
                                        totalHours += (report.hoursperWeek !== null && report.hoursperWeek !== "") ? parseFloat(report.hoursperWeek)
                                                : 0;
                                        orgWorkingHours = report.orgWorkingHours;
                                    }

                                    total = (totalavailable >= 100 ? 0
                                            : 100 - totalavailable);
                                    hours = (totalHours >= orgWorkingHours ? 0
                                            : (orgWorkingHours - totalHours)
                                                    .toFixed(2));
                                    text += " </tbody>";
                                    text += " <tfoot>";
                                    text += " <tr>";
                                    text += " <td colspan='2'></td>";
                                    text += " <td>Total</td>";
                                    text += " <td><div class='width180px'>"
                                            + total + "</div></td>";
                                    text += " <td><div class='width180px'>"
                                            + hours + "</div></td>";
                                    text += " </tr>";
                                    text += " </tfoot>";
                                    text += " </table>";
                                    text += " </td>";
                                    text += "</tr>";
                                }
                                text += "</tbody>";
                                text += "</table>";
                                $("#exportOptions").show();
                                $("#availableAssociatesReporttableList").empty();
                                $("#availableAssociatesReporttableList")
                                        .append(text);
                            } else {
                                $("#exportOptions").hide();
                                $("#availableAssociatesReporttableList").empty();
                                $("#availableAssociatesReporttableList")
                                        .append(
                                                "<div class='dataTables_empty'></div>");
                            }

                        }
                    })
        } else {
            return false;
        }
    }
</script>
      <script>
      function reportExport(type){
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
    	  window.location.href =  sourceleadUrl+"/availableAssociatereportExport?&type="+type+"&userIds="+associates+"&projectsIds="+projects+"&fromDate="+fromDate+"&toDate="+toDate;
  	}
      
      </script>
      
      <script>
  function newActions(){
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
	$("html").click(function(){
		$(".newActPop").slideUp(200);
	});
	$(".overIcon, .newActPop").click(function(e){
	 	e.stopPropagation();
	});	

</script>
      
      
      <jsp:include page="../masterFooter.jsp" />