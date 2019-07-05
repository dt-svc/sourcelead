<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Dashboard Reports</h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/configureCustomDashBoard">Dashboarrd list </a> &raquo; Add Dashboard Reports</div>
    </section>
    
    <div class="project_wrap newDashSection clearfix">
        <div class="newDashLeft">
            <ul id="nav" class="clearfix">
                <li><a href="#" class="active">Performance Reports</a></li>
                <li><a href="#">HR Reports</a></li>
                <li><a href="#">Financial Reports</a></li>
                <li><a href="#">Associate Reports</a></li>
                <li><a href="#">Project Reports</a></li>
                <li><a href="#">Pay roll Reports</a></li>
                <li><a href="#">Immigration Reports</a></li>
                <li><a href="#">User usage Reports</a></li>
            </ul>
        </div>

        <div class="newDashRight">
            <div class="clearfix" id="tab-content">
            <div id="tab1">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate
                            <span class="gridList clearfix">
                                <p class="col-md-6">
                                    <input id="a1" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="a1" class="checkbox-custom-label paddingright">List View</label>
                                </p>
                                <p class="col-md-6">
                                    <input id="a2" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="a2" class="checkbox-custom-label paddingright">Chart View</label>
                                </p>
                            </span>
                        </label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report
                            <span class="gridList clearfix">
                                <p class="col-md-6">
                                    <input id="aa1" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="aa1" class="checkbox-custom-label paddingright">List View</label>
                                </p>
                                <p class="col-md-6">
                                    <input id="aa2" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="aa2" class="checkbox-custom-label paddingright">Chart View</label>
                                </p>
                            </span>                            
                        </label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report
                            <span class="gridList clearfix">
                                <p class="col-md-6">
                                    <input id="ab1" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="ab1" class="checkbox-custom-label paddingright">List View</label>
                                </p>
                                <p class="col-md-6">
                                    <input id="ab2" value=" " class="checkbox-custom" name="mailIds[]" type="radio">
                                    <label for="ab2" class="checkbox-custom-label paddingright">Chart View</label>
                                </p>
                            </span>                             
                        </label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="e" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="e" class="checkbox-custom-label paddingright">Vendor(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="f" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="f" class="checkbox-custom-label paddingright">Client(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="g" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="g" class="checkbox-custom-label paddingright">Applicant(s) Decline</label>
                    </li>
                    <li class="clearfix">
                        <input id="h" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="h" class="checkbox-custom-label paddingright">Applicant(s) Backout</label>
                    </li>
                    <li class="clearfix">
                        <input id="i" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="i" class="checkbox-custom-label paddingright">Job(s) Pipeline</label>
                    </li>
                    <li class="clearfix">
                        <input id="j" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="j" class="checkbox-custom-label paddingright">Applicant(s) by Source</label>
                    </li>  
				</ul>
            </div>
            
            <div id="tab2">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>
            
            <div id="tab3">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>
            
            <div id="tab4">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>

            <div id="tab5">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>
            
            <div id="tab6">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>
            
            <div id="tab7">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
                    </li> 
				</ul>
            </div>
            
            <div id="tab8">
                <ul>
                    <li class="clearfix">
                        <input id="a" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="a" class="checkbox-custom-label paddingright">Offer Acceptance Rate</label>
                    </li>
                    <li class=" clearfix">
                        <input id="b" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="b" class="checkbox-custom-label paddingright">Applicants Joined Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="c" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="c" class="checkbox-custom-label paddingright">Recruiter(s) Report</label>
                    </li>
                    <li class="clearfix">
                        <input id="d" value=" " class="checkbox-custom" name="mailIds[]" type="checkbox">
                        <label for="d" class="checkbox-custom-label paddingright">My Performance Report</label>
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