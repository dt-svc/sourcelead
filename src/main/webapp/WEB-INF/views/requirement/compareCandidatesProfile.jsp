<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->
<%-- 
     @author : Pavan Kumar Jampana.
     this script is for quick view of Candidate.
--%>

<script>
function compareRecommendedUserd(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	
	var result = '';
    result = result +'<div class="sideMask" style="display: block;"></div>';
    result = result +'<div class="compare_resume">';
    result = result +'    <a onclick="closeCompare();" class="close_btn text-danger pull-right btn btn-danger btn-sm">X</a>';
    result = result +'    <div class="row">';
    result = result +'        <div class="col-md-2">';
    result = result +'                    <h4 class="text-primary">Candidate Compare</h4>';
    result = result +'            <div class="listHeadr text-center clearfix">';
    result = result +'                <div class="form-group clearfix mt40">';
    result = result +'                    <label>Java Developer</label>';
    result = result +'                </div>';
    result = result +'                <div class="form-group clearfix">';
    result = result +'                    <label>ID400568</label>';
    result = result +'                </div>';
    result = result +'            </div>';
    result = result +'                <div class="resumeFields">';
    result = result +'                <ul class="list-unstyled">';
    result = result +'                    <li><i class="fa fa-hourglass-2" title="Profile Score"></i><span class="resumeValues">50% to 60%</span></li>';
    result = result +'                    <li><i class="fa fa-usd" title="Rate / Salary"></i><span class="resumeValues">50$ to 60$ hour </span></li>';
    result = result +'                    <li><i class="fa fa-briefcase" title="Experience"></i><span class="resumeValues">3yrs to 5yrs</span></li>';
    result = result +'                    <li><i class="fa fa-map-marker" title="Location"></i><span class="resumeValues">Hyderabad, Telangana - 50081</span></li>';
    result = result +'                    <li><i class="fa fa-calendar" title="Notice Period"></i><span class="resumeValues">15 to 30 Days</span></li>';
    result = result +'                    <li><i class="fa fa-graduation-cap" title="Notice Period"></i><span class="resumeValues">MCA Computers</span></li>';
    result = result +'                    <li><i class="fa fa-cc-visa" title="Visa"></i><span class="resumeValues">H1 Visa</span></li>';
    result = result +'                    <li><i class="fa fa-code" title="Skills"></i><span class="resumeValues">Java, MySql ...</span></li>';
    result = result +'                </ul>';
    result = result +'            </div>';
    result = result +'        </div>';
    result = result +'     <div class="profilecompare-view col-md-10">';
    result = result +'         <div class="row">';
    result = result +'               <!-- Profile view -->';
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                <!-- Profile Header -->';
    result = result +'                    <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" data-placeholder="SelectCandidate" id="candidateScore">';
    result = result +'                            <option selected>---Select Candidate---</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                <div class="profilecompare-widget_hdr clearfix">';
    result = result +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
    result = result +'                    <img src="images/24.jpg" alt="Profile Image">';
    result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
    result = result +'                </div>';
    result = result +'                 <div class="text-center clearfix col-md-12 profile-progress">';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'            <div class="progress">';
    result = result +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
    result = result +'            80%';
    result = result +'            </div>';
    result = result +'            </div> ';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'                 </div>';
    result = result +'                <div class="text-center clearfix">';
    result = result +'                <p>';
    result = result +'                    <span>';
    result = result +'                    <strong>Jaden Smith</strong>';
    result = result +'                    </span>';
    result = result +'                </p>';
    result = result +'                <p>Primary:<span class="paddingLeft">Java</span></p>';
    result = result +'                <p>Email:<span class="paddingLeft">johnsmith.2016@gmail.com</span></p>';
    result = result +'                <p>Ph:<span class="paddingLeft">8855996645</span></p>';
    result = result +'                </div>';
    result = result +'                </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                <div class="profile_personalInfo">';
    result = result +'                <ul class="list-unstyled mb clearfix">';
    result = result +'                <li>10%</li>';
    result = result +'                <li>200 $ per hours</li>';
    result = result +'                <li>6 Years</li>';
    result = result +'                <li>Hyderabad, <br>india</li>';
    result = result +'                <li>15 Days</li>';
    result = result +'                <li>MCA Computers</li>';
    result = result +'                <li>Valid Visa</li>';
    result = result +'                <li>JAVA, HiberNet, JDBC ..</li>';
    result = result +'                </ul>';
    result = result +'               <button type="button" value="" class="btn btn-primary btn-sm btn-block mt10 clearfix">View Profile</button>';
    result = result +'                </div>';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                </div>';
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                <!-- Profile Header -->';
    result = result +'                                            <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" data-placeholder="SelectCandidate" id="candidateScore">';
    result = result +'                            <option selected>---Select Candidate---</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                <div class="profilecompare-widget_hdr clearfix">';
    result = result +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
    result = result +'                    <img src="images/24.jpg" alt="Profile Image">';
    result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
    result = result +'                </div>';
    result = result +'                 <div class="text-center clearfix col-md-12 profile-progress">';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'            <div class="progress">';
    result = result +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
    result = result +'            80%';
    result = result +'            </div>';
    result = result +'            </div> ';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'                 </div>';
    result = result +'                <div class="text-center clearfix">';
    result = result +'                <p>';
    result = result +'                    <span>';
    result = result +'                    <strong>Jaden Smith</strong>';
    result = result +'                    </span>';
    result = result +'                </p>';
    result = result +'                <p>Primary:<span class="paddingLeft">Java</span></p>';
    result = result +'                <p>Email:<span class="paddingLeft">johnsmith.2016@gmail.com</span></p>';
    result = result +'                <p>Ph:<span class="paddingLeft">8855996645</span></p>';
    result = result +'                </div>';
    result = result +'                </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                <div class="profile_personalInfo">';
    result = result +'                <ul class="list-unstyled mb clearfix">';
    result = result +'                <li>10%</li>';
    result = result +'                <li>200 $ per hours</li>';
    result = result +'                <li>6 Years</li>';
    result = result +'                <li>Hyderabad, <br> india</li>';
    result = result +'                <li>15 Days</li>';
    result = result +'                <li>MCA Computers</li>';
    result = result +'                <li>Valid Visa</li>';
    result = result +'                <li>JAVA, HiberNet, JDBC ..</li>';
    result = result +'                </ul>';
    result = result +'               <button type="button" value="" class="btn btn-primary btn-sm btn-block mt10 clearfix">View Profile</button>';
    result = result +'                </div>';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                </div>';
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                                            <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" data-placeholder="SelectCandidate" id="candidateScore">';
    result = result +'                            <option selected>---Select Candidate---</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <div class="profilecompare-widget_hdr clearfix">';
    result = result +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
    result = result +'                    <img src="images/24.jpg" alt="Profile Image">';
    result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
    result = result +'                </div>';
    result = result +'                 <div class="text-center clearfix col-md-12 profile-progress">';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'            <div class="progress">';
    result = result +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
    result = result +'            80%';
    result = result +'            </div>';
    result = result +'            </div> ';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'                 </div>';
    result = result +'                <div class="text-center clearfix">';
    result = result +'                <p>';
    result = result +'                    <span>';
    result = result +'                    <strong>Jaden Smith</strong>';
    result = result +'                    </span>';
    result = result +'                </p>';
    result = result +'                <p>Primary:<span class="paddingLeft">Java</span></p>';
    result = result +'                <p>Email:<span class="paddingLeft">johnsmith.2016@gmail.com</span></p>';
    result = result +'                <p>Ph:<span class="paddingLeft">8855996645</span></p>';
    result = result +'                </div>';
    result = result +'                </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                <div class="profile_personalInfo">';
    result = result +'                <ul class="list-unstyled mb clearfix">';
    result = result +'                <li>10%</li>';
    result = result +'                <li>200 $ per hours</li>';
    result = result +'                <li>6 Years</li>';
    result = result +'                <li>Hyderabad, <br> india</li>';
    result = result +'                <li>15 Days</li>';
    result = result +'                <li>MCA Computers</li>';
    result = result +'                <li>Valid Visa</li>';
    result = result +'                <li>JAVA, HiberNet, JDBC ..</li>';
    result = result +'                </ul>';
    result = result +'               <button type="button" value="" class="btn btn-primary btn-sm btn-block mt10 clearfix">View Profile</button>';
    result = result +'                </div>';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                </div>';
    result = result +'                <div class="profilecompare-widget col-md-3">';
    result = result +'                                            <div class="clearfix mb10">';
    result = result +'                        <select class="chosen-select form-control" data-placeholder="SelectCandidate" id="candidateScore">';
    result = result +'                            <option selected>---Select Candidate---</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                            <option>candidate - 80%</option>';
    result = result +'                        </select>';
    result = result +'                    </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <div class="profilecompare-widget_hdr clearfix">';
    result = result +'                <div class="profileImg text-center clearfix mt10 positionRelative">';
    result = result +'                    <img src="images/24.jpg" alt="Profile Image">';
    result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';
    result = result +'                </div>';
    result = result +'                 <div class="text-center clearfix col-md-12 profile-progress">';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'            <div class="progress">';
    result = result +'            <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:80%">';
    result = result +'            80%';
    result = result +'            </div>';
    result = result +'            </div> ';
    result = result +'                     <!-- Progress Bar -->';
    result = result +'                 </div>';
    result = result +'                <div class="text-center clearfix">';
    result = result +'                <p>';
    result = result +'                    <span>';
    result = result +'                    <strong>Jaden Smith</strong>';
    result = result +'                    </span>';
    result = result +'                </p>';
    result = result +'                <p>Primary:<span class="paddingLeft">Java</span></p>';
    result = result +'                <p>Email:<span class="paddingLeft">johnsmith.2016@gmail.com</span></p>';
    result = result +'                <p>Ph:<span class="paddingLeft">8855996645</span></p>';
    result = result +'                </div>';
    result = result +'                </div>';
    result = result +'                <!-- Profile Header -->';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                <div class="profile_personalInfo">';
    result = result +'                <ul class="list-unstyled mb clearfix">';
    result = result +'                <li>10%</li>';
    result = result +'                <li>200 $ per hours</li>';
    result = result +'                <li>6 Years</li>';
    result = result +'                <li>Hyderabad, <br> india</li>';
    result = result +'                <li>15 Days</li>';
    result = result +'                <li>MCA Computers</li>';
    result = result +'                <li>Valid Visa</li>';
    result = result +'                <li>JAVA, HiberNet, JDBC ..</li>';
    result = result +'                </ul>';
    result = result +'               <button type="button" value="" class="btn btn-primary btn-sm btn-block mt10 clearfix">View Profile</button>';
    result = result +'                </div>';
    result = result +'                <!-- profile_personalInfo -->';
    result = result +'                </div>';
    result = result +'        </div>';
    result = result +'    </div>';
    result = result +'</div></div>';
    
	$("#candidateCompare").empty();
	$("#candidateCompare").append(result);
	}
</script>

 <script>
    function closeCompare(){
         $(".compare_resume, .sideMask").fadeOut(200);
    }
    </script>
