<link href="//fonts.googleapis.com/css?family=Lato:100,300,400" rel="stylesheet">
<script type="text/javascript" id="apiscript" src="/resources/js/all.js?burst=1"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<style type="text/css">
		body{
			font-family: 'Lato', sans-serif;
		    font-size: 14px;
		    line-height: 1.42857143;
		    color: #505050;
		    background-color: #ffffff;
		}
		#selectedContacts,#selectedFriends{width:715px;height:200px;margin-top: 20px;}
		.holder{
			/* width: 735px; */
	    	float: left;
		}
		.center{margin:0 auto;text-align:center}
		.text-primary{color:Orange}
		.padTop50{
			padding-top:50px;
		}
		.sc_regform-holder {
		    width: 500px;
		    margin: 0 auto;
		    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    		font-size: 14px;
    		margin-bottom: 50px;
    		margin-top: 30px;
		}
		.sc_formheader {
		    float: left;
		    background-color: #EBEBEB;
		    padding: 10px;
		    border-top-right-radius: 5px;
		    border-top-left-radius: 5px;
		    text-shadow: 1px 0px 0px #FAF7F7;
		    width: 512px;
		}
		.sc_row-last {
		    border-bottom: 1px solid #eee;
		    padding-bottom: 20px;
		    border-bottom-right-radius: 5px;
		    border-bottom-left-radius: 5px;
		}
		.sc_dp img {
		    width: 80px;
		    border-radius: 500px;
		    border: 2px solid #fff;
		}
		.sc_formheader-desc {
		    width: 400px;
		    word-wrap: break-word;
		    margin-left: 15px;
		}
		.sc_row {
		    clear: both;
		    float: left;
		    padding-top: 10px;
		    border-left: 1px solid #eee;
		    border-right: 1px solid #eee;
		    width: 500px;
		    padding-left: 15px;
		    padding-right: 15px;
		}
		.sc_row input{
			background-color: #ffffff;
		    border: 1px solid #cccccc;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
		    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
		    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
		    -webkit-transition: border linear 0.2s, box-shadow linear 0.2s;
		    -moz-transition: border linear 0.2s, box-shadow linear 0.2s;
		    -o-transition: border linear 0.2s, box-shadow linear 0.2s;
		    transition: border linear 0.2s, box-shadow linear 0.2s;
		    display: inline-block;
		    height: 30px;
		    padding: 4px 6px;
		    margin-bottom: 10px;
		    font-size: 14px;
		    line-height: 20px;
		    color: #555555;
		    vertical-align: middle;
		    -webkit-border-radius: 4px;
		    -moz-border-radius: 4px;
		    border-radius: 4px;
		}
		.input-xlarge {
		    width: 270px;
		}
		.sc_row-left {
		    width: 120px;
		    color: #77492F;
		    line-height: 30px;
		}
		.sc_row-sep {
		    padding-right: 10px;
		    line-height: 30px;
		}
		.sr_row-profurl {
		    width: 360px;
		    text-overflow: ellipsis;
		    overflow: hidden;
		}
		
	</style>

<script type="text/javascript">
	var loadProfileInformation = function(response,service){
		if(response.responseStatus.type=="success"){
			getProfileDetails(response,service)
		}
	}
	 function socialInviterScripts() {
		 var sourceleadUrl ='${sourceleadUrl}';
		 var sourceleadWebUrl = sourceleadUrl.replace('/app','');
		 var cssUrl = sourceleadWebUrl+"/resources/css/";
		 var jsUrl = sourceleadWebUrl+"/resources/js/";
		 
         socialinviter.load({
             dock:false,
             showmodal: true,
             showmore: true,
             showsearch: true,
             selectall:false,
             style: {
                 header: {
                     "background-color": "#234FAD",
                     "color": "#fff"
                 }
             },
             apikeys:{
            	 "facebook":{
                     "consumerKey":"2066960683592341",
                     "consumerSecret":"a5e28872fb1b6456392f1c58b1649ca0"
                 }
             },
             callbacks: {
                 "loaded": function (service, response) {
                 },
                 "back": function (event, service) {
                 },
                 "send": function (event, service, recipients) {
                     socialinviter.close();
                     if(socialinviter.getProduct()=="contactimporter"){
	                     $("#selectedContacts").html("");
	                     var len = recipients.length;
	                     
	                     var selectedRecipients = "";
	                     
	                     for(var i=0;i<len;i++){
	                    	 if(i!=0){
	                    		 selectedRecipients += ", ";	 
	                    	 }
	                    	 selectedRecipients += recipients[i].name.first_name+" "+recipients[i].name.last_name;
	                    	 selectedRecipients += "<"+recipients[i].email[0]+">";
	                     }
	                     $("#selectedContacts").val(selectedRecipients);
                     }
                     
                 },
                 "proceed": function (event, service) {

                 },
                 "login": function (service, response) {
                	 loadProfileInformation(response,service);
                 },
                 "fetched": function (service, status) {
                	 contactsImportSucess(service, status);
                 },
                 "post": function (service, status) {
                	 socialPostSucess(service);
                 }
             },
             
             path: {
                 css: cssUrl,
                 js: jsUrl,
                 authpage :sourceleadWebUrl + "/resources/oauth.html",
                 apiurl:sourceleadUrl+"/ws/getAPIService"
             }
         });
        /*  var shareObj = {
             title: "Sourcelead",
             link: "http://www.sourcelead.com",
             picture: "http://sourcelead.com/images/logo.png",
             description: "Engage your user using socialinviter services.",
             comment: "Allows your business to promote through multiple social networks."
         }; */
         
        /*  socialinviter.socialconnect.getUserInformation(); */
        /*   socialinviter.socialpost.setPostValues(shareObj);  */

     }
 
</script>

<script type="text/javascript">
	function uploadCsv() {
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#fuploadform').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#fuploadform')[0];
      		var formData = new FormData(form); 
			//It will escape all the special characters
			$("#store_csv").attr("disabled", "disabled");
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeCsv?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
								
						},
					});
	 } 
	
</script>

