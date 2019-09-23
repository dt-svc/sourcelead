<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="//fonts.googleapis.com/css?family=Lato:100,300,400" rel="stylesheet">
<script src="//socialinviter.com/assets/js/libraries/jquery.js"></script>
<script type="text/javascript" id="apiscript" src="/resources/js/all.js?burst=1"></script>
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
			width: 735px;
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
</head>
<body>
<section class="content-wraper">
<h1 class="text-primary center">
     Check out our demo</h1>

	<div class="enguser center">
	    <h2>Promote Your website with the Best Social Tools on the Web.</h2>
	</div>
	
<hr />
<table>
	<tr>
		<td class="padTop50">
			<div>Import your contacts:</div>
			<div class="socialinviter" type="contactimporter"></div>
			<div>
				<textarea id="selectedContacts"></textarea>
			</div>
		</td>
	</tr>
	<tr>
		<td class="padTop50">
			<div>Invite your friends:</div>
			<div class="socialinviter holder break" type="friendsinviter"></div>
		</td>
	</tr>
	<tr>
		<td class="padTop50">
			<div>Login using social networks:</div>
			<div class="socialinviter holder break" type="socialconnect" ></div>
		</td>
	</tr>
	<tr>
		<td class="padTop50">
			<div>Post content programmatically: </div>
			<div class="socialinviter holder break" type="socialpost"></div>
		</td>
	</tr>
</table>


<br/>
<br/>

<div class="sc_regform-holder fl break">
    <div class="break sc_formheader">
        <div class="fl sc_dp"><img src="http://socialinviter.com/assets/img/icons/avator.JPG" id="profileimage"></div>
        <div class="fl sc_formheader-desc ng-binding" id="aboutme"></div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Profile url</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right sr_row-profurl">
            <a target="_blank" href="#" id="profileurl"></a>
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Id</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="loginid">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Display name</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="logindisplayname">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">First name</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="loginfirstname">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Last name</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="loginlastname">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Email id</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="loginemail">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Gender</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="logingender">
        </div>
    </div>
    <div class="sc_row">
        <div class="fl sc_row-left">Date of birth</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="logindob">
        </div>
    </div>
    <div class="sc_row sc_row-last">
        <div class="fl sc_row-left">Location</div>
        <div class="fl sc_row-sep">:</div>
        <div class="fl sc_row-right">
            <input type="text" placeholder="Not available" class="input-xlarge ng-pristine ng-valid" id="loginlocation">
        </div>
    </div>
</div>
<br/>
<br/>
</section>
<script type="text/javascript">
var shareObj = {
        title: "Sourcelead",
        link: "http://www.sourcelead.com",
        picture: "http://sourcelead.com/images/logo.png",
        description: "Engage your user using socialinviter services.",
        comment: "Allows your business to promote through multiple social networks."
    };
 var setPostValues = function (obj) {
    if (obj) {
        if (obj.title) {
            postdatavalues.title = obj.title;
        }
        if (obj.link) {
            postdatavalues.link = obj.link;
        }
        if (obj.picture) {
            postdatavalues.picture = obj.picture;
        }
        if (obj.description) {
            postdatavalues.description = obj.description;
        }
        if (obj.comment) {
            postdatavalues.comment = obj.comment;
        }
    }
 }
 function getPostValueDetails(){
	 setPostValues(shareObj);
	 return postdatavalues;
  }
</script>


<script type="text/javascript">
	var loadProfileInformation = function(response){
		if(response.responseStatus.type=="success"){
			var details = response.data.details;
			$("#profileimage").attr("src",details.photo);
			$("#aboutme").html(details.aboutMe);
			$("#profileurl").html(details.profileUrl).attr("href",details.profileUrl);
			$("#loginid").val(details.id);
			$("#logindisplayname").val(details.displayName);
			$("#loginfirstname").val(details.firstName);
			$("#loginlastname").val(details.lastName);
			$("#loginemail").val(details.email);
			$("#logingender").val(details.gender);
			$("#logindob").val(details.dob);
			$("#loginlocation").val(details.location);
		}
	}
	 $(document).ready(function () {
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
                	 loadProfileInformation(response);
                	 $("body,html").animate({scrollTop:$(".sc_regform-holder").offset().top-20});
                 }
             },
             
             path: {
                 css: "/resources/css/",
                 js: "/resources/js/",
                 authpage : "http://localhost:8080/app/resources/oauth.html",
                 apiurl:"http://localhost:8080/app/ws/getAPIService"
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
         /* socialinviter.socialpost.setPostValues(shareObj);  */

     });
 
</script>
<jsp:include page="../masterFooter.jsp" />


