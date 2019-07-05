<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>




<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Dashboard Modules</h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/configureCustomDashBoard">Dashboarrd list </a> &raquo; Add Dashboard Modules</div>
    </section>
    
    <div class="project_wrap newDashSection newDashSectionMdu clearfix">
        <div class="newDashLeft">
            <ul id="nav" class="clearfix">
                <li><a href="#" class="active">Clients</a></li>
                <li><a href="#">Vendors</a></li>
                <li><a href="#">Jobs</a></li>
                <li><a href="#">Candidates</a></li>
                <li><a href="#">Onboard & Offboard</a></li>
                <li><a href="#">Projects</a></li>
                <li><a href="#">timesheets</a></li>
                <li><a href="#">Expenses</a></li>
                <li><a href="#">Contracts</a></li>
                <li><a href="#">Finance</a></li>
                <li><a href="#">Leave Management</a></li>
                <li><a href="#">Payroll</a></li>
            </ul>
        </div>

        <div class="newDashRight">
            <div class="clearfix" id="tab-content">
            <div id="tab1">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Client by Contact</label>
                    </li>
				</ul>
            </div>
            
            <div id="tab2">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Vendors by Contact</label>
                    </li>
				</ul>
            </div>
            
            <div id="tab3">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Assigned Jobs</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Jobs Pipeline</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Interview</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">Job Fair</label>
                    </li> 
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Jobs Pipeline</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Applicant Permission</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">Submit to Client</label>
                    </li> 
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined</label>
                    </li>                                       
				</ul>
            </div>
            
            <div id="tab4">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Recently Added</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Requested Documents</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Purchase Orderlist</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">Sales Order list</label>
                    </li>
				</ul>
            </div>

            <div id="tab5">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Candidates on onboarding</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Current onboard</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Current Offboard</label>
                    </li>
				</ul>
            </div>
            
            <div id="tab6">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Project Requests</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Assigned Project Tasks</label>
                    </li>
				</ul>
            </div>

            <div id="tab7">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Timesheets</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Submited for Approval</label>
                    </li>
				</ul>
            </div>

            <div id="tab8">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Expenses</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Submited  Expenses</label>
                    </li>
				</ul>
            </div>

            <div id="tab9">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Read for Contract</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Counter Sign</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Sign</label>
                    </li>   
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Waiting for Approval</label>
                    </li>                                        
				</ul>
            </div>

            <div id="tab10">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Candidates ready for Invoices</label>
                    </li>
				</ul>
            </div>

            <div id="tab11">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Leave Management</label>
                    </li>
				</ul>
            </div>

            <div id="tab12">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">My Pays</label>
                    </li>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Approve Payroll</label>
                    </li>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Submited It Forms</label>
                    </li>                                        
				</ul>
            </div>


        </div>
        
    <div class="footerstyelsNew">
        <div class="pull-right mr">
            <input value="Add" class="btn btn-primary mr" type="button">
        </div>
    </div>

        </div>

	</div>
  </section>
  
  <script>
$(document).ready(function(){
    $('#tab-content div').hide();
    $('#tab-content div:first').show();

    $('#nav li').click(function() {
        $('#nav li a').removeClass("active");
        $(this).find('a').addClass("active");
        $('#tab-content div').hide();

        var indexer = $(this).index(); 
        $('#tab-content div:eq(' + indexer + ')').fadeIn(); 
    });  
});

</script>
<jsp:include page="../masterFooter.jsp" />
