<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0; user-scalable=0;">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<link rel="manifest" href="/manifest.json">
<title>::SourceLead::</title>
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"
	rel="stylesheet">
<link
	href="resources/css/bootstrap.min.css"
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
    header {
        width: 100%; background-color: #368ee0; height: 54px; box-shadow: 0px 0px 2px rgba(0,0,0,0.4);
    }
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
    .clear {
        clear: both;
    }
    .logo {
        width: 210px; height: 54px; background-color: #fff; text-align: center; vertical-align: middle; display: table-cell;
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
        width: 85%; position: relative; margin: 0px auto; background-color: #fff; padding: 8px 12px 14px 12px;
        border-radius: 2px; top: 32%;
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
    .browseWrap {
        width: 92%; border: 1px solid #dedede; border-radius: 4px; box-sizing:  border-box; position: relative;
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
</style>
<body>
	<header>
		<div class="logo">
		<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
 	 		<img src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}">
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
		<div class="clear"></div>
	</header>
	<section>
		<div class="browseOut">
			<div class="browseProgress">
            <div class="browseProgressInn">
            <div class="labText" id='progressText'>Text here...</div>
                <div class="progress progress-striped active">
                    <div class="progress-bar progress-bar-success" id='progressBar'>0%</div>
                </div>
            </div>                
            </div>
			<div class="browse">
				<h2>Candidate (Resume) Bulk Import</h2>
				<div class="browseScroll">
					<div class="browseWrap">
						<div class="browseInn">
							<input type="file" class="btnHide" id='uploadFile' multiple="multiple"> <a
								href="javascript:void(0)"><i class="fa fa-file-text-o"></i>Browse</a>
						</div>
						<span class="filename">Upload File</span>
						<div class="clear"></div>
						<div class="addFilBtn">
							<i class="fa fa-plus"></i>
						</div>
					</div>
				</div>

			</div>
			<div class="browseBtns">
				<button id='upload'>
					<i class="fa fa-upload"></i> Upload
				</button>
				<button class="cleBtn" id='clear'>Clear</button>
			</div>
			<div class="clear"></div>
		</div>
		<div class="editor">
			<span>
<!-- 				<div class='copy btn' data-clipboard-target='#log'><i class="fa fa-copy"></i></div> -->
				<h3>File load log</h3>
				<div class="editText" id='log'></div>
			</span>
		</div>
	</section>

	<footer>
		<div class="copyright">
			&copy; 2017 SourceLead <sup>&reg;</sup> , All Rights Reserved
		</div>
	</footer>
	<div style='display:none' id='browserBar'>
		<div class="browseWrap">
			<div class="browseInn">
				<input type="file" id='abc' class="btnHide" multiple="multiple">
				<a href="javascript:void(0)"><i class="fa fa-file-text-o"></i>Browse</a>
			</div>
			<span class="filename">Upload File</span>
			<div class="clear"></div>
			<div class="abcdef"><i class="fa fa-trash-o"></i></div>
		</div>	
	</div>
</body>

<script src="resources/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<!-- <script src="resources/js/clipboard.min.js" type="text/javascript"></script> -->
<script>
var count = 1;
	$(document).ready(function() {
		$(".browseInn input:file").change(function() {
			var fileName = $(this).val();
			$(this).parent().parent().find('.filename').html(fileName.substring(fileName.lastIndexOf("/")+1).substring(fileName.lastIndexOf("\\")+1));
		});
		$(".addFilBtn").click(function() {
			$(".browseScroll").append($("#browserBar").html().replace("abc", "uploadFile"+(count ++)).replace("abcdef", "removeFilBtn"));
			$(".removeFilBtn").click(function() {
				$(this).parent(".browseWrap").remove();
			});
			$(".browseInn input:file").change(function() {
				var fileName = $(this).val();
				$(this).parent().parent().find('.filename').html(fileName.substring(fileName.lastIndexOf("/")+1).substring(fileName.lastIndexOf("\\")+1));
			});
		});
		$("#clear").click(function() {
			count = 1;
			$(".removeFilBtn").each(function(i) {  $(this).click(); });
			$("input[type=file]").val('');
			$('.filename').html("Upload File");
		});
		$("#upload").click(function() {
			var data1 = new FormData();
			var j = 0;
			$("input[type=file]").each(function(i) {
				var files = document.getElementById($(this).attr('id')).files;
				for(var i=0;i<files.length;i++)
					data1.append("inputFile"+(j++), files[i]);
			});
			uploadid = new Date().getTime();
			getUploadStatus();
			data1.append("uploadid", uploadid);
			$(".browseProgress").show();
			$("#progressBar").html('0%');
			$(".progress div").css("width", "0%");
			$(".editText").html("");
			$.ajax({
				  xhr: function() {
				    var xhr = new window.XMLHttpRequest();
					$("#progressText").html('Please wait while uploading files...');
				    xhr.upload.addEventListener("progress", function(evt) {
				      if (evt.lengthComputable) {
				        var percentComplete = evt.loaded / evt.total;
				        percentComplete = parseInt(percentComplete * 100);
				        $("#progressBar").html(percentComplete+'%');
				        $(".progress div").css("color", "#fff").css("width", percentComplete + "%");
				        if (percentComplete === 100) {
// 				        		$("#progressBar").html('0%');
				        		$("#progressText").html('Please wait while parsing the uploaded files...');
				        		$(".progress div").css("width", percentComplete + "%");
// 				        		$("#progressBar").html('0%');
				        }

				      }
				    }, false);

				    return xhr;
				  },
				  url: 'upload-resumes',
				  type: "POST",
				  processData: false,
				  contentType: false,
				  data: data1,
				  success: function(result) {
				    console.log(result);
				  },
				  error: function(request) {
					  uploadid = 0;
					  alert(request.responseText.indexOf("Maximum upload size of") != -1 ? "Maximum allowed size is 2GB" : "Operation failed, please try agian");
				  }
				});
		});
		
	});
	
	var uploadid = 0;
	function getUploadStatus() {
		if(uploadid > 0) {
			$.post("bulk-resume-import-log", {uploadid: uploadid}, function(data) {
				$(".editText").append(data);
				if(data.indexOf('Completed!') === -1) {
					window.setTimeout('getUploadStatus()', 1000);
					window.setTimeout('getUploadFileStatus()', 1000);
				} else {
					$(".browseProgress").hide();
					$("#clear").click();
				}
			});
		}
	}
	
	function getUploadFileStatus() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-counts", {uploadid: uploadid}, function(data) {
			if(data !== "" && data.indexOf('Completed!') === -1) {
				var i = data.split(" ")[0];
				var count = data.split(" of ")[1].split(" ")[0];
				percentComplete = parseInt(i)/parseInt(count);
				$(".progress div").css("width", parseInt(percentComplete*100) + "%");
				$("#progressBar").html(parseInt(percentComplete*100)+'%');
				$("#progressText").html(data);
			} else if(data !== "")
				$("#progressText").html(data);
		});
	}
	
	function exportLog() {
		if(uploadid == 0)
			return;
		$.post("bulk-resume-import-export-log", {uploadid: uploadid}, function(data) {
			if(data !== "")
				$("#progressText").html(data);
		});
	}
</script>

</html>
