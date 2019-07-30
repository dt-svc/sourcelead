<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
var uploadflag= true;
		window.onload = function () {
			var refreshId = setInterval(function(){
				var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
				if(uploadflag == true){
					xlsupload.getUploadInprogressProgressBar("Associates",dateFormat);
				  }else{
					     clearInterval(refreshId);
						 $(".browseProgress").hide();
						 $("#upload_button").removeAttr("disabled");
						 xlsupload.getUploadInprogressProgressBar("Associates",dateFormat);
				  }
			},1000);
		 };
</script>


<section class="content-wraper">
	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Import Associates Information</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_atsUrl}/usersList">Associates List</a> &raquo; Import Associates Information </div>
    </section>
	<div id="loading"></div>
	<div class="content-area clearfix" id="dataFileImportDiv">
	 <div class="success_pop" id="msgDiv" style="display: none;"></div>
		<form:form id="upload_asso_info_form" name="upload_asso_info_form" method="post" enctype="multipart/form-data">
			<div class="clearfix form-area form-horizontal toggle-div remove-div mt">
			<div class="row minHyewtriuwe rwrghek	fghjqwehjgfwej tkjreight">
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
					<!-- Download File button -->
					<div class="clearfix mb row">
					
						<div class="col-xs-5 col-md-5">
							<a href="${SourceLeadWeb_atsUrl}/downloadTemplateForAssociateInfomation"
								class="btn btn-success btn-block" title="Download Associate Template"> <i class="fa fa-download"></i>&nbsp;&nbsp; Associate Info Template
							</a>
						</div>
						<div class="col-xs-5 col-md-5">
							<div class="btn-group btn-group-justified">
								<div class="btn-group">
									<input type="button" class="btn btn-primary" id="upload_button"
										name="upload_button" onclick="importAssociateBankDetail();"
										value="Import">
								</div>
								<div class="btn-group">
									<input type="reset" class="btn btn-danger" value="Reset"
										onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/uploadAssociateInformation'">
								</div>
							</div>
						</div>
					</div>
					
					<!-- Attach File button -->

					<div class="dropzone">
						<div class="clearfix"></div>
						<div class="file-upload clearfix text-center">
							<div class="col-md-12" id="">
								<label class="bg-default"> <i aria-hidden="true"
									class="fa fa-paperclip" title="Attach file"></i> <br /> <input
									type="file" name="file" field-name="File Upload"
									style="display: none;" id="uploadBtn"
									data-validation="required validate_importFile" multiple>
								</label>
								<div class="clearfix"></div>
								<label id="newUploadFile"></label> 
								<input type="text" hidden readonly id="dataFilePath" value="">
							</div>
							<div class="center-div">
								<input id="uploadFile" placeholder="" disabled="disabled"
									style="width: 100%;" />
							</div>
						</div>

						<!--  Downloaded File show here -->
						<div class="col-xs-3 alert alert-success fade in hidden"
							style="position: absolute; top: 10px;" id="fileAttached">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<div>File Holder</div>
						</div>
						<!--  Downloaded File show here -->
					</div>
					
					<div id="errorDownloadDiv" style="display: none"></div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
					<div class="bgInfo bg-info clearfix text-center">
						<span><i class="fa fa-question" aria-hidden="true"></i></span> <span
							class="info-msg clearfix mt">
							<p class="clearfix mt">
								According to this template, the Users need to <span
									class="text-danger"><strong>Import Required
										Fields</strong></span>
							</p>
							<p>
							  <label><i class="fa fa-user"></i>&nbsp;&nbsp;Associate Email</label>
							  </p>
						</span>
					</div>
				</div>
			</div>
		</div>
		</form:form>
	</div>
	<div class="require_wrap_table datatable_filter">
	<h5 id="uploadXlsHistoryDivTitle" style="display: none;">Failed Records</h5>
		<div id="errorDataDIV"></div>
        </div>
        
        
    <div class="project_tabs_inn_table project_tabs_inn_table_a datatable_filter dataSeaTop" id="recentHistoryDiv">
	<!-- <h5 id="uploadXlsHistoryDivTitle"  style="display: block;">Recent Uploads History</h5>
	    	<table id="uploadXlsHistoryDiv" class="display" cellspacing="0" width="100%"></table>
	    	<input type="hidden"  id="uploadedPercentage" value="0"> -->
		</div>	 	
</section>

<div class="success_pop" id="selector11" style="display: none;">
    <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
</div>

<script type="text/javascript" src="/resources/js/xlsupload.js"></script>

<script type="text/javascript">
document.getElementById("uploadBtn").onchange = function () {
	$('#newUploadFile').text($(this).val().replace(/C:\\fakepath\\/i, ''));
	fileUpload("uploadBtn", "dataMigration");
};
</script>


<script type="text/javascript">
function importAssociateBankDetail(){
	var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
	var fileUrl = $("#dataFilePath").val();
	var uploadType = "associateUpload";
	var headerPosition = $("#headerPosition").val();

	var frm = $('#upload_asso_info_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	var sourceleaAtsdUrl = '${SourceLeadWeb_atsUrl}';
	
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	var form = $('#upload_asso_info_form')[0];
	var formData = new FormData(form); 
	
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		   	type: "POST",
		   	data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
    	    url: sourceleaAtsdUrl+"/uploadAssociateInfomation?",	   
 		    success : function(json) {
 		    	  
	 		    	   $("#loading").removeAttr("style");
			    	    if(json.count == 0){
			    	    	$("#msgDiv").html("Should fill the atleast mandatory Fields").css({"background":"#d9534f"})
			 			}else{
			 				 $("#msgDiv").html(json.insertedCount+ " Associates updated");
			 			}
			    	    msgFadein();
			 			if(json.pfErrorData != null && json.pfErrorData.length >0){
			 				showFailedExcelData(json);
			 				importassociteSel(json);
			 			}
			    	},
		 		   error: function(e){
			        }
			 });
}
</script>


<script type="text/javascript">
function cancelFileImport(){
	var sourceleaAtsdUrl = '${SourceLeadWeb_atsUrl}';
	location.replace(sourceleaAtsdUrl+"/usersList");
}
</script>

<script>
  function showFailedExcelData(json){
		  var result = "";
	    	 result += '<table id="errorDataList" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Assoicate Id</th>';
	         result += '    <th>Associate Email*</th>';
	         result += '    <th>UAN Number</th>';
	         result += '    <th>PF Number</th>';
	         result += '    <th>ESI Number</th>'; 
	         result += '    <th>Reson</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         var data = json.pfErrorData;
	    	 for(i=0;i < data.length;i++){
	    		 var report = data[i];
	    		 result += '<tr>';
		         result += '<td><div class="tblminwidth-100">'+report.userId+'</div></td>';
		         result += '<td><div class="tblminwidth-175">'+report.email+'</div></td>';
		         result += '<td><div class="tblminwidth-175">'+report.uanNumber+'</div></td>';
		         result += '<td><div class="tblminwidth-175">'+report.pfNumber+'</div></td>';
		         result += '<td><div class="tblminwidth-175">'+report.esinumber+'</div></td>';
		         result += '<td><div class="colorRed tblminwidth-175">'+report.reason+'</div></td>';
		         result += '  </tr>';
	    	 }
	    	
	         result += '  </tbody>';
    		 result += '</table> ';
    		 $("#errorDataDIV").empty();
    		 $("#errorDataDIV").append(result);
    		 $("#loading").removeAttr('style');
    		 excelDataPagination();
  }
  
  function excelDataPagination(){
		$('#errorDataList').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#errorDataList").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
  </script>
<script>
function importassociteSel(json){			
			$("#recentHistoryDiv").hide();
			$("#uploadXlsHistoryDivTitle").show();
		}
</script>
<script>
	function msgFadein() {
		$("#msgDiv").fadeIn(200);
        $('#msgDiv').delay(2000).fadeOut('slow');
	}
    </script> 
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

