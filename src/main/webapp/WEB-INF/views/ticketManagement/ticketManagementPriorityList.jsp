<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Pritory</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a> &raquo; Ticket Priority List</div>
    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
        <div class="col-md-2 padding_right">
          <ul class="list-unstyled actTicketUL">
            <li><a href="/app/ticketManagementTypeList"><i class="fa fa-ticket mr10"></i>Type</a></li>
            <li><a href=/app/ticketManagementCategoryList><i class="fa fa-navicon mr10"></i>Category</a></li>
            <li class="actTicketList"><a href=/app/ticketManagementPriorityList><i class="fa fa-asterisk mr10"></i>Priority</a></li>
            <li><a href=/app/ticketManagementStatusList><i class="fa fa-check-circle mr10"></i> Status</a></li>
          </ul>
        </div>
        <div class="col-md-10 padding_left">
			<div class="project_tabs projectTabsborder">
				<div id="tab-1">
          <div class="positionRelative timesheet_head clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a class="newAdBtn mr5" title="Add" id="addPop">+ Add</a></li>
              <li class="hidden">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="candidate_wrap_table datatable_filter">
           <table style="width: 100%;" id="ticketManagementPriorityTable">
              <thead>
                <tr>
                    <th>Icon</th>
                    <th>Priority</th>
                    <th>Description</th>
                    <th>Color</th>
                    <th>Action</th>
                  </tr>
              </thead>
              <tbody id="ticketPriorityList">
                  
                  </tbody>
            </table>
          </div>
			</div>
				
		  </div>
		  
        </div>
      </div>
    </div>
  </section>
  <!-- Footer Area --> 
<div class="mask"></div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span id="ticketPriorityFormHead" >Add Priority</span><i class="closePop pull-right popClose"></i></div>
        <form id="addTicketPriorityForm" name="" action="" method="post">
        <input type="hidden" id="ticketPriorityId" name="id" value="0"/>
        <div class="popupContent">
            <div class="clearfix mb">
                <label class="noPad">Name <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input class="form-control" placeholder="Name" name="name" type="text" id="ticketPriorityName" field-name="Name" data-validation="required validate_length length1-100">
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Color</label>
                <div class="clearfix">
                    <div class="input-group">
                        <input id="favcolor" type="text" class="form-control valid" readonly="" name="colorPic" placeholder="Color Pic">
                        <span class="input-group-addon" style="padding: 2px 10px;">
                        <input type="color" id="favcolorP" name="colorCode" style="width: 20px; height: 20px;" onchange="colorPicker();">
                        </span> 
                      </div>
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Icon</label>
				<div class="clearfix">
					<div class="input-group col-md-12">
						<span class="input-group-btn">
							<label class="input-group-btn"> 
								<span class="btn btn-danger" onchange="uploadPriorityImage();">
									<i class="fa fa-folder-open-o"></i> &nbsp;Browse File 
									<input type="file" style="display: none;" multiple id="uploadBtn" class="upload">
									<input type="hidden" id="dataFilePath">
								</span>
							</label>
						</span>
						<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume ...">
					</div>

					<div class="clearfix mt10 text-muted">
						<i class="fa fa-question-circle text-info"></i>&nbsp; Upload icon images ...
					</div>
				</div>
			</div>
            <div class="clearfix mb">
                <label class="noPad">Description</label>
                <div class="clearfix">
					<textarea placeholder="Description" name="description" id="ticketPriorityDesc" class="form-control" rows="2"></textarea>
                </div>
            </div>
        </div>
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" onclick="saveTicketPriority();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="cancelTicketPriority();"> Cancel</button>
        </div>
	</form>
</div>

<div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket Priority"></p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTicketPriorityStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>

  <script type="text/javascript">
  $(document).ready(function() {
	  getTicketPriorityList();
	});
function experienceRadio() {
	var radioCheck = document.getElementById("experienceRadio");
    var experienceRadio = document.getElementById("experienceDiv");
    if (radioCheck.checked == true){
       experienceRadio.style.display = "block";
    } else {
       experienceRadio.style.display = "none";
    }
}
function applicableForAll() {
	document.getElementById("experienceDiv").style.display="none";
}
$(function(){
$("#addPop").click(function() {
	$(".mask, #modalTasktype").fadeIn();
});
$(".popClose").click(function() {
	$(".mask, #modalTasktype").fadeOut();
});
	});

  function colorPicker() {
	var favColor = document.getElementById("favcolorP").value;
	document.getElementById("favcolor").value = favColor;
    document.getElementById("colorBox").style.background = favColor;
}
</script>

 <jsp:include page="../masterFooter.jsp" />	

 