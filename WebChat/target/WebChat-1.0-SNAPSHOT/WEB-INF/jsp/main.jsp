<%-- 
    Document   : mainSite
    Created on : 2016-nov-16, 12:49:30
    Author     : sundi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

    <head>
         <script type="text/javascript" src="${pageContext.request.contextPath}/Resources/SCRIPT/myScript.js" ></script>
        <link href="${pageContext.request.contextPath}/Resources/CSS/style.css" rel="stylesheet" type="text/css" >
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.1.1.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avatar - WebChat</title>
    </head>
    <body>
       
    <!-- Navbar goes here -->   
        <jsp:include page="header.jsp"/>
 
    <!-- Mainpage Layout here -->
         <jsp:include page="contentMain.jsp"/>

    <!--Footers Layout here -->
        <jsp:include page="footer.jsp"/>

        <!--Script for menu & autocompleteSearch -->
        <script>
            console.log("hrj");
             $( document ).ready(function(){
              $('.button-collapse').sideNav({
      menuWidth: 320, // Default is 240
      edge: 'left', // Choose the horizontal origin
      closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
      draggable: true // Choose whether you can drag to open on touch screens
    }
     );
  

  $('input.autocomplete').autocomplete({
    data: {
      <c:forEach var="listValue" items="${users}">
      ${listValue.getUsername()} : null,
      </c:forEach>
      "Google": 'http://placehold.it/250x250'
    }
  });
        })
        </script>
        
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>
    </body>
</html>
