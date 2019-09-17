<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0; user-scalable=0;">
<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<link rel="manifest" href="/manifest.json">
<title>::SourceLead::</title>
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="/resources/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<style>
body {
        font-family: Arial, Helvetica, sans-serif; font-weight:400; font-size:14px; margin:0px 0px; padding:0px 0px;
        overflow-x:hidden; background-color: #f1f1f1;  box-sizing: border-box;
        }
    input, select, textarea, label {
        font-family: Arial, Helvetica, sans-serif;
        }
        .links {
        	display: none;
        	width:80%; margin: 0px auto;
        }
        .links li:FIRST-CHILD {
        	width: 10% !important;
        	text-align: left !important;
        }
        .links li {
    float: left;
    list-style: none;
    padding: 10px;
    width: 26%;
    color: #337ab7;
    cursor: pointer;
    text-decoration: underline;
    text-align: center;
}
.links li:laST-CHILD {
	width: 34%;
}
    header {
        width: 100%; background-color: #368ee0; height: 54px; box-shadow: 0px 0px 2px rgba(0,0,0,0.4);
    }
    .plupload_header {display: none !important;}
    .text_logo {
    font-size: 18px;
font-weight: bold;
text-align: center;
line-height: 52px;
overflow: hidden;
text-overflow: ellipsis;
width: 160px;
white-space: nowrap;
vertical-align: middle;
font-style: normal;
color: #375d81;
margin: 0px auto;
    }
    div.title {
    	float: left;
    	color: white;
    	font-size: 16px;
    font-weight: 500;
    padding: 15px 0px 0px 20px;
    }
    #uploader { width: 80%; margin: 0px auto; }
    .clear {
        clear: both;
    }
    .logo {
        float: left; width: 210px; height: 54px; background-color: #fff; text-align: center; vertical-align: middle; display: table-cell;
    }
    .logo img {
        width: auto; max-height: 50px; max-width: 190px; padding: 1px 0px 0px 0px;
    }
    section {
        padding: 0px 0px 70px 0px;
    }
    .browseOut {
        width: 1050px; margin: 15px auto; position: relative;
    }
    .browseProgress {
        position: absolute; width: 100%; height: 100%; background:rgba(0,0,0,0.3); z-index: 100;
        border-radius: 3px; display:none;
    }
    .browseProgressInn {
        width: 50%; position: relative; margin: 0px auto; background-color: #fff; padding: 8px 12px 14px 12px;
        border-radius: 2px; top: 17%;
    }
    .browseProgressInn .progress {
        margin: 0px 0px; position: relative;
    }
    .labText {
        width: 100%; font-size: 14px; font-weight: 500; color: #454545; padding: 0px 0px 5px 0px;
    }
    .browse {
        width: 650px; background-color: #fff; margin: 0px 0px; display: block; padding: 8px 12px 12px;
        box-shadow: 0px 0px 2px rgba(0,0,0,0.2); border-radius: 2px; float: left;
    }
    h2 {font-size: 16px; padding: 0px 20px;}
    .browseWrap {
        border: 1px solid #dedede; border-radius: 4px; box-sizing:  border-box; position: relative;
        margin: 4px 0px;
    }    
    .browseInn {
        width:130px; display: block; height: 34px; position: relative; float: left;
    }
    .btnHide {
        width: 100%; opacity: 0; position: relative; z-index: 10; height: 34px; cursor: pointer;
    }
    .browseInn a {
        position: absolute; width: 100%;  left: 0px; top:0px; height: 34px;
        display: block; text-align: center; padding: 7px 0px 0px 0px;  box-sizing: border-box;
        border-radius: 2px; text-decoration: none; font-size: 15px; font-weight: 500; color: #fff;
        background-image: linear-gradient(to bottom, #2c84d6 0px, #2170b9 100%); background-repeat: repeat-x;
    }
    .browseInn .fa-file-text-o {
        display: inline-block; padding: 0px 10px 0px 0px;
    }
    .browse span {
        width: 290px; float: left; display: block; padding: 8px 0px 0px 10px; font-size: 14px; font-weight: 500;
        color: #666; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;
    }
    .editor {
        width: 1050px; background-color: #fff; margin: 0px auto; display: block; padding:40px 20px 20px;
        box-shadow: 0px 0px 2px rgba(0,0,0,0.2); border-radius: 2px; position: relative; box-sizing: border-box;
    }
    .editor h3 {
        position: absolute; top: 2px; display: inline-block; background-color: #fff; padding: 6px 40px; left: 100px;
        border: 1px solid #ccc; border-radius: 3px; font-size: 16px; color: #454545; font-weight: 600;
    }
    .editText {
        width: 100%; min-height: 300px; max-height: 400px; resize: none; font-size: 14px; font-weight: 500;
        color: #333; padding: 15px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 5px; overflow: auto;
    }
    .addFilBtn {
        position: absolute; right: -44px; top: 0px; height: 34px; width: 40px; box-sizing: border-box;
        text-align: center; border-radius: 2px;
        font-size: 22px; color: #175894; padding: 4px 0px 0px 0px; cursor: pointer;
    }
    .removeFilBtn {
        position: absolute; right: -44px; top: 0px; height: 34px; width: 40px; box-sizing: border-box;
        text-align: center; border-radius: 2px;
        font-size: 20px; color: #d43f3a; padding: 3px 0px 0px 0px; cursor: pointer;
    }
    .browse h2 {
        padding: 0px 0px; margin: 0px 0px; font-size: 18px; font-weight: 500; color: #454545; padding: 5px 0px 5px 0px;
    }
    .browseScroll {
        display: block; max-height: 160px; overflow: auto;
    }
    .browseBtns {
        float: left; padding: 10px 8px; background-color: #fff; box-shadow: 0px 0px 2px rgba(0,0,0,0.2);
        border-radius: 2px; margin: 30px 18px 15px;
    }
    .browseBtns button {
        text-align: center; padding: 0px 0px 0px 0px;  box-sizing: border-box; height: 36px; width: 120px; margin: 0px 3px;
        border-radius: 2px; text-decoration: none; font-size: 15px; font-weight: 500; color: #fff; border: none;
        background-image: linear-gradient(to bottom, #2c84d6 0px, #2170b9 100%); background-repeat: repeat-x;
        cursor: pointer;
    }
    .browseBtns button.cleBtn{
        background-image: linear-gradient(to bottom, #999 0px, #777 100%); background-repeat: repeat-x;
    }
    .browseBtns button i {
        display: inline-block; padding: 0px 5px 0px 0px;
    }
    footer {
        background: #fff; clear: both; padding: 12px 0px 12px 0px; transition: all ease-in-out 0.2s; text-align: center;
        bottom: 0; right: 0; width: 100%;  box-shadow: 0px 0px 2px rgba(0,0,0,0.2); position: fixed;
        color: #454545; font-size: 14px;
    }
    .editText .a {
    		color: green;
    }
    .editText .b {
    		color: maroon;
    }
    .editText .c {
    		color: red;
    }
    .copy {
    	margin-bottom: -30px;
float: right;
width: 20px;
height: 24px;
border: 1px solid gray;
padding: 2px
    }
    .mb {
    margin-bottom: 15px;
    }
    .mt {
    margin-top: 15px;
    }
    .plupload_file_name {
    	width: 700px !important;
    }
	.upresume { text-align: center; display: block;}
	.upresumeInn { display: inline-block; padding: 0px 15px;}
</style>
<body>
	<header>
	
		<div class="logo">

		 <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
 	 <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
 	 	<img data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_displayName == 'Professional' ? 'Job Seeker' : SourceLeadWeb_displayName}" class="img-responsive mt5">
 	 </c:if>
 	  <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
 	  <img data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" class="img-responsive mt5">
 	 </c:if>
 	 </c:if>
 	 	<c:if test="${empty SourceLeadWeb_org.fileContentType}">
				<div class="text_logo">
				 <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${!empty SourceLeadWeb_orgName}">
					<span class="tbldata-dotdot" title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</c:if>
				</c:if>
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
				<span class="tbldata-dotdot" title="${SourceLeadWeb_displayName == 'Professional' ? 'Job Seeker' : SourceLeadWeb_displayName}">${SourceLeadWeb_displayName == 'Professional' ? 'Job Seeker' : SourceLeadWeb_displayName}</span>
				</c:if>
				</div>
		</c:if>
		</div>
		<div class='title'>
			<c:choose>
				<c:when test="${requirementUUID !=''}">Applicants</c:when>
				<c:otherwise>Candidate</c:otherwise>
			</c:choose>
			 (Resume) Bulk Import
		</div>
		
		 <div class="clear"></div>
		 <div class="pull-right" style="padding: 10px 30px 0px 0px">
			  <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER' && SourceLeadWeb_prodSupportLoginUserRole == null}">
			  <a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}" title="Source Lead">Home</a> &raquo; <a href="${SourceLeadWeb_atsUrl}/candidatesList" title="Source Lead">Candidates List</a>
			  </c:if>
			  
			   <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
			   <a href="${SourceLeadWeb_sourceleadUrl}/productionSupportdashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}" title="Source Lead">Home</a>
			   </c:if>
			  <div class="clear"></div>
			  
			  
			  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
			  <a href="${SourceLeadWeb_sourceleadUrl}/professionalDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}" title="Source Lead">Home</a>
			  </c:if>
			  
			  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
			  <a href="${SourceLeadWeb_sourceleadUrl}/recruiterDashboard/${SourceLeadWeb_orgLocationId}/${SourceLeadWeb_displayName}" title="Source Lead">Home</a>
			  </c:if>
		</div>
	</header>
<div class="browseProgress">
            <div class="browseProgressInn">
            <div class="labText" id='progressText'>Please wait while uploading the file(s). This step may take more time depending on the size of the file.</div>
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" id='progressBar'>0%</div>
                </div>
            </div>                
            </div>

<c:if test="${requirementUUID !=''}">
	<div class="upresume mb mt">
	<div class="upresumeInn">
		<input type="checkbox" name="needApplicantPermit" id="needApplicantPermit">&nbsp;&nbsp;<label for="needApplicantPermit">Need applicant permission</label>
		</div>
		<div class="upresumeInn">
			<input type="checkbox" name="notifyToApplicant" value="Y" id="notifyToApplicant">&nbsp;&nbsp;<label for="notifyToApplicant">Notify to applicant</label>
		</div> 
	</div>
</c:if>            
            
<div id="uploader" style="margin-top: 40px">
    <p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
</div>
<div class='links'>
	<ul>
		<li onclick='location.reload();'>Refresh</li>
		<li id='exportLog'>Download Upload Log</li>
		 <li id='exportFailed'>Download Failed Resumes</li>
		<li id='exportDuplicate'>Download Duplicate Resumes</li> 
	</ul>
</div>
	<footer>
		<div class="copyright">
			&copy; 2017 SourceLead <sup>&reg;</sup> , All Rights Reserved
		</div>
	</footer>
</body>

<script src="/resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<!-- <script src="resources/js/clipboard.min.js" type="text/javascript"></script> -->
<script>
var sourceleadUrl = '${sourceleadUrl}';

	var uploadid = 0;
	var win1, win2;
	function getUploadStatus() {
		if(uploadid > 0) {
			$.post("bulk-resume-import-log", {uploadid: uploadid}, function(data) {
				$(".editText").append(data);
// 				if(data.indexOf('Completed!') === -1) {
					win1 = window.setTimeout('getUploadStatus()', 1000);
					win2 = window.setTimeout('getUploadFileStatus()', 1000);
// 				} else {
// 					$(".browseProgress").hide();
// 					$("#clear").click();
// 				}
			});
		}
	}
	
	function getUploadFileStatus() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-counts", {uploadid: uploadid}, function(data) {
			if(data !== "") { // && data.indexOf('Completed!') === -1
				var i = data.split(" ")[0];
				var count = data.split(" of ")[1].split(" ")[0];
				percentComplete = parseInt(i)/parseInt(count);
				$(".progress div").css("width", parseInt(percentComplete*100) + "%");
				$("#progressBar").html(parseInt(percentComplete*100)+'%');
				$("#progressText").html(data);
				win2 = window.setTimeout('getUploadFileStatus()', 1000);
			}
// 			} else if(data !== "")
// 				$("#progressText").html(data);
		});
	}
	
</script>
<input type='file' style='display:none' multiple="multiple" id='filesSelected'/>
<script type="text/javascript" src="/resources/pluploadjs/plupload.full.min.js"></script>

<script type="text/javascript" src="/resources/pluploadjs/jquery.plupload.queue.min.js" charset="UTF-8"></script>
<link type="text/css" rel="stylesheet" href="/resources/pluploadjs/jquery.plupload.queue.css" media="screen" />

<script type="text/javascript">
$(function() {
	var requirementUUID = "${requirementUUID}";
	var reqQuery = "", needApplicantPermitQuery = "", notifyToApplicantQuery = ""; 
	if(requirementUUID !== "") {
		reqQuery = "requirementUUID=" + requirementUUID;
		needApplicantPermitQuery = ($("#needApplicantPermit").length > 0 && $("#needApplicantPermit").is(":checked")) ? "&needApplicantPermit='Y'" : "";
		notifyToApplicantQuery = ($("#notifyToApplicant").length > 0 && $("#notifyToApplicant").is(":checked")) ? "&notifyToApplicant='Y'" : "";
	}
	/* alert("RequirementUUID : " + requirementUUID); */
    // Setup html5 version
    $("#uploader").pluploadQueue({
        // General settings
        
        runtimes : 'html5,flash,silverlight,html4',
        url : 'upload-resumes' + (requirementUUID !== "" ? "requirementUUID=" + requirementUUID : "") + ($("#needApplicantPermit").length > 0 && $("#needApplicantPermit").is(":checked") ? "&needApplicantPermit=Y" : "") + ($("#notifyToApplicant").length > 0 && $("#notifyToApplicant").is(":checked") ? "&notifyToApplicant=Y" : ""),
        chunk_size : '400kb',
        rename : false,
        dragdrop: true,
         
        filters : {
            // Maximum file size
            max_file_size : '100mb',
            // Specify what files to browse for
            mime_types: [
                {title : "Resume files", extensions : "doc,docx,pdf"},
                {title : "Zip files", extensions : "zip"}
            ]
        },
 
        // Resize images on clientside if we can
        resize: {
            width : 200,
            height : 200,
            quality : 90,
            crop: true // crop to exact dimensions
        },
 
 
        // Flash settings
        flash_swf_url : '/resources/pluploadjs/Moxie.swf',
     
        // Silverlight settings
        silverlight_xap_url : '/resources/pluploadjs/Moxie.xap',
        init: {
        	
    		PostInit: function(up) {
    			$("#uploader_container").attr('title', '');
    			$(".plupload_droptext").html('Upload or Drag files here');
    			$(".plupload_button.plupload_start").unbind('click');
    			$(".plupload_button.plupload_start").click(function() {
    				$.post("check-session", {}, function(data) {
    					if(data === "YES") {
    						uploadid = new Date().getTime();
    						up.settings.url = 'upload-resumes?uploadid='+uploadid + (requirementUUID !== "" ? "&requirementUUID=" + requirementUUID : "") + ($("#needApplicantPermit").length > 0 && $("#needApplicantPermit").is(":checked") ? "&needApplicantPermit=Y" : "") + ($("#notifyToApplicant").length > 0 && $("#notifyToApplicant").is(":checked") ? "&notifyToApplicant=Y" : "");
    						up.start();
    					} else {
    						alert("No Session found");
    						location.href='/login';
    					}
    				});
    				return false;
    			});
    		},
    		
    		UploadComplete: function(up, files) {
                $(".links").show();
                $(".browseProgress").hide();
				$("#clear").click();
				clearTimeout(win1);
				clearTimeout(win2);
				$(".plupload_file_action a").attr("title", "Parsed");
            },
            
            ChunkUploaded: function(up, file, info) {
				if(file.percent >= 90) {
					$(".browseProgress").show();
					$("#progressBar").html('0%');
					$(".progress div").css("width", "0%");
					getUploadStatus();
				}
            }, 
            
            FilesAdded: function() {
            	$(".plupload_file_action a").attr('title', "Remove");
            }
        }
    });
    
    $(".plupload_header").hide();
});

$(function() {
	$("#exportLog").click(function() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-export-log", {uploadid: uploadid}, function(data) {
			if(data !== "")
				location.href = '/app/downloadFile.html?file='+data;
			else
				alert("Unable to locate logs for this upload");
		});
	});
	
	$("#exportFailed").click(function() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-export-failed", {uploadid: uploadid}, function(data) {
			if(data !== "" && data.indexOf("Resumes Found") === -1)
				location.href = '/app/downloadFile.html?file='+data;
			else if(data !== "" && data.indexOf("Resumes Found") !== -1)
				alert(data);
			else
				alert("Unable to locate logs for this upload");
		});
	});
	
	$("#exportDuplicate").click(function() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-export-duplicate", {uploadid: uploadid}, function(data) {
			if(data !== "" && data.indexOf("Resumes Found") === -1)
				location.href = '/app/downloadFile.html?file='+data;
			else if(data !== "" && data.indexOf("Resumes Found") !== -1)
				alert(data);
			else
				alert("Unable to locate logs for this upload");
		});
	});
	
	
});
</script>
</html>
