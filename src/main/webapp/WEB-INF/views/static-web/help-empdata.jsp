<!--   HTML   -->
<jsp:include page="../staticHeader.jsp" />  
<section class="helpWrap">
  <div class="helpWrap-Inn">
    <div class="searchWrap"> <span><i class="fa fa-search"></i>
      <input placeholder="Search ..." class="searchHome" type="text">
      </span> <span>
      <input value="Search" class="searchBtn" onclick="window.location.href='http://localhost:8080/app/openRequirementsRedirect'" type="button">
      </span>
      <div class="clear"></div>
    </div>
  </div>
  <div class="clear"></div>
</section>
<section class="breadcrumbWrap">
  <div class="breadcrumbWrap-in">
    <ol class="breadcrumb">
      <li class="breadcrumb-item"><a href="#">Home</a></li>
      <li class="breadcrumb-item"><a href="#">Library</a></li>
      <li class="breadcrumb-item active">Data</li>
      <div class="clear"></div>
    </ol>
  </div>
  <div class="clear"></div>
</section>
<section class="searchresultWrap">
  <div class="media">
    <div class="media-body">
      <h4 class="media-heading heading-big-normal">Getting Started with SourceLead</h4>
      <h5 class="text-muted heading-small-normal">Just getting started? Here's a How To on uploading your information into Humi!</h5>
      
      <!-- inner Media -->
      <div class="media">
        <div class="media-left"> <img src="/resources/css/static-web/images/24.jpg" class="media-object writerimages" style="width:35px"> </div>
        <div class="media-body">
          <div class="writers-text">
            <p><span class="text-muted">Written</span> by Heather S</p>
            <p class="text-muted">Updated over a week ago</p>
          </div>
        </div>
      </div>
      <!-- inner Media Ends --> 
    </div>
  </div>
    <div class="about-content">
        <p>
        The fastest way to get started with Humi is to upload your employee information using our Import feature. You can use a spreadsheet that you have on hand or one of our templates.
        </p>
        <ol>
<li>In the "Employee" module, click on the "Bulk" button.</li>
<li>Select the "Import" feature.</li>
<li>Click on '<b>download our CSV template'</b> or '<b>download this abbreviated template</b>' to get started. &nbsp;</li>
<li>Fill out all employee details as listed on the template <b>OR </b>amend a spreadsheet of your own to match the fields in the downloaded template.</li>
<li>Once the document is ready and saved, you can click or drag and drop the .csv file to upload it into The Box. The Box is magical.</li>
<li>After the upload completes, you can review the data. Once you're happy with the extracted data, click '"<b>Save to Humi"</b>.</li>
<li>That's it, you're done! Go back to your Employee module and see what it looks like.</li>
</ol>
        <p>Exporting and Importing can be much easier than going into employee profiles and editing in Humi one at a time.</p>

<p>Did you know... some changes in Humi will generate a confirmation email to the manager and/or employee. If you're making multiple changes or corrections, you can pause those notifications! From the Dashboard, click the Company button and set Employee Notifications to OFF. Just remember to turn them back on when you're done: this will also suppress all vacation request notifications! </p>
    
    </div>
    <div class="about-content-footer">
        <p>Did this answer your question?</p>
        <div class="smile-images">
        <i class="fa fa-frown-o fa-3x" aria-hidden="true" title="Smile"></i>
        <i class="fa fa-smile-o fa-3x" aria-hidden="true" title="Neutra Face"></i>
        <i class="fa fa-meh-o fa-3x" aria-hidden="true" title="Disappointed"></i>
        </div>
    </div>
</section>
<jsp:include page="../staticFooter.jsp" />