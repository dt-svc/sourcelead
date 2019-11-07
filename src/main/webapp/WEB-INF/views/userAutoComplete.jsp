
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

      <title>Insert title here</title>
      <title>jQuery UI Autocomplete functionality</title>
      <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
      <script src="http://code.jquery.com/jquery-1.10.2.js?staticTime=${staticTime}"></script>
      <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js?staticTime=${staticTime}"></script>
      <style>
         .projec_Label { display: block; font-weight: bold; margin-bottom: 1em; }
         .project_description { margin: 0; padding: 0; }
         }
      </style>
      <!-- Javascript -->
      <script>
         $(function() {
            var projects = [
            {
               value: "java",
               label: "Java",
               desc: "write once run anywhere",
               img : "/resources/images/at.png",
            },
            {
               value: "jquery-ui",
               label: "jQuery UI",
               desc: "the official user interface library for jQuery",
	       img : "/resources/images/in.png"	
            },
            {
               value: "Bootstrap",
               label: "Twitter Bootstrap",
               desc: "popular front end frameworks ",
               img : "/resources/images/tr.png",
            }
         ];
         $( "#project" ).autocomplete({
            minLength: 0,
            source: projects,
            focus: function( event, ui ) {
               $( "#project" ).val( ui.item.label );
                  return false;
               },
            select: function( event, ui ) {
               $( "#project" ).val( ui.item.label );
               $( "#project-id" ).val( ui.item.value );
               $( "#project-description" ).html( ui.item.desc );
               return false;
            }
         }).data("ui-autocomplete")._renderItem = function (ul, item) {     
            var inner_html = '<a><div class="list_item_container"><div class="image"><img src="http://localhost:8080/app/userImage"></div><div class="label"><h3> Reputation:  ' + item.desc + '</h3></div><div class="description">' + item.label + '</div></div></a><hr/>';
            return $("<li></li>")
                    .data("item.autocomplete", item)
                    .append(inner_html)
                    .appendTo(ul);
        };


       
         });
      </script>
      
</head>
 
<body>
      
         <div id="project-label" class="projec_Label">Select a project (type "a" for a start):</div>
         <input id="project">
         <input type="hidden" id="project-id">
         <p id="project-description" class="project_description"></p>
         
</body>
</html>