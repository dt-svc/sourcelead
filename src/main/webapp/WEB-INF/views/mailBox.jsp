<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
 window.onload = function() {
	 getMails('Inbox');
};
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Mailbox</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Mailbox</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="action-btns clearfix hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal">
        <div class="row">
		<!-- Mail view area -->
			<!-- Mail side-bar -->
			<div class="col-md-3">
          <a class="btn btn-primary btn-block mb compose" href="${sourceleadUrl}/hotlistMail">
		  <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
			Compose
		  </a>

          <div class="mail-box">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li class="active"><a href="#" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox
                  <span class="label label-primary pull-right">12</span></a></li>
                <li><a href="#" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
               <!--  <li><a href="#"><i class="fa fa-file-text-o"></i> Drafts</a></li>
                <li><a href="#"><i class="fa fa-filter"></i> Junk <span class="label label-warning pull-right">65</span></a>
                </li> -->
                <li><a href="#"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->
          <div class="mail-box">
            <div class="mail-box-header">
              <h3>Labels</h3>
            </div>
            <div class="box-body no-padding">
              <ul class="nav nav-pills nav-stacked">
                <li><a href="#"><i class="fa fa-circle-o text-danger"></i> Important</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-warning"></i> Promotions</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-primary"></i> Social</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
			<!-- mail side-bar ends -->
			<div class="col-md-9">
			 <div class="mail-box mail-content">
			 <div class="mail-box-header">
              <h3 class="box-title">Inbox</h3>
              <!-- <div class="box-tools pull-right">
                <div class="has-feedback">
                  <input type="text" placeholder="Search Mail" class="form-control input-sm">
                  <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
              </div> -->
              <!-- /.box-tools -->
            </div>
			
			<!-- /.box-tools -->
			<div class="mailbox-controls">
                <!-- Check all button -->
                <button class="btn btn-default checkbox-toggle" type="button"><i class="fa fa-square-o"></i>
                </button>
				<button class="btn btn-danger" type="button"><i class="fa fa-trash-o"></i></button>
                <div class="btn-group hidden">
                  <button class="btn btn-danger" type="button"><i class="fa fa-trash-o"></i></button>
                  <button class="btn btn-warning" type="button"><i class="fa fa-reply"></i></button>
                  <button class="btn btn-info" type="button"><i class="fa fa-share"></i></button>
                </div>
                <!-- /.btn-group -->
                <button class="btn btn-default" type="button"><i class="fa fa-refresh"></i></button>
               
               <!--  <div class="pull-right">
                  1-50/200
                  <div class="btn-group">
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-left"></i></button>
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-right"></i></button>
                  </div>
                  /.btn-group
                </div> -->
                <!-- /.pull-right -->
              </div>
			  <!-- /.box-tools -->
			
	<!-- mail box table -->
			<div class="table-responsive mailbox-messages">
                 <div id="mailBoxList"></div>
              </div>
	<!-- mail box table ends -->   

			</div>
			</div>

		<!-- Mail view area -->

			

			</div>
		   </div>
		</div>
  </section>
  
  
  <script>
  function getMails(type){
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMails?${_csrf.parameterName}=${_csrf.token}&type="+type,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
              			 
              			var result = ''; 
              			result = result +'<table id="mails" class="table table-hover table-striped">';
              			result = result +'<tbody>';
              			
              			 var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 
		                    	result = result +' <tr>';
		              			result = result +'<td>';
		              			result = result +'<div class="squaredFour">';
		              			result = result +'<input type="checkbox" value="None" id="squaredFour" name="check" checked />';
		              			result = result +'<label for="squaredFour"></label>';
		              			result = result +'</div>';
		              			result = result +'</td>';
		              			result = result +'<td class="mailbox-star"><a href="#"><i class="fa fa-star text-yellow"></i></a></td>';
		              			result = result +'<td class="mailbox-subject">'+opt.subject+'</td>';
		              			result = result +'<td class="mailbox-date">'+opt.calDays+' days ago</td>';
		              			result = result +'</tr>';
		                    }
		                    
              			
	                 	 $("#mailBoxList").empty(); 
	                     $("#mailBoxList").append(result); 
	                     pagination(); 
			    	} 
                }else{
 	    	         
               	var result ="";
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	           		result = result +'<table id="mails" class="table table-hover table-striped">';
	           		result = result +'           <thead>';
	           		result = result +'           <p class="text-center text-muted"><i class="fa fa-envelope fa-3x"></i><br/><br/>No Mails In Your '+type+' </p>';
	           		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#mailBoxList").empty();
				    $("#mailBoxList").append(result); 
				    pagination();  
               }
         },
           
      }); 
	  
  }
  
  </script>
  
  <script>
function pagination(){
    $("#mails").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>



<jsp:include page="masterFooter.jsp" />