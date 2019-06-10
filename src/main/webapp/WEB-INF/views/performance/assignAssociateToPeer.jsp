
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="popupWrap popupWrapFixed" id="peerModaltype">
      <div class="popupHead"><span>Add Peers</span><i class="closePop pull-right popClose" onclick="closePeerPopup();"></i></div>
         <div id="competenceLoading"></div>
        <form:form id="competence_form" name="competence_form" method="post">
	        <div class="popupContent">
					<div id="assignPeerDiv"></div>
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="associatePeers" onclick="savePeers();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="closePeerPopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>


  
  <script>
  function assignToPeer(userId){
	  $(".mask, #peerModaltype").fadeIn();
	  getPeersList();
  }
  
  function closePeerPopup(){
	  $(".mask, #peerModaltype").fadeOut();
  }
  </script>
  
  <script>
  function getPeersList(){
	  $("#assignPeerDiv").empty();
	  $("#assignPeerDiv").append("xcvzxcvxzcvxzvxzv");
  }
  </script>