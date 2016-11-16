<%-- 
    Document   : mainSite
    Created on : 2016-nov-16, 12:49:30
    Author     : sundi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/Resources/CSS/style.css" rel="stylesheet" type="text/css" >
        <script src="${pageContext.request.contextPath}/Resources/SCRIPT/myScript.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avatar - WebChat</title>
    </head>
    <body>
         <!-- Navbar goes here -->
<nav class="col s12">
    <div class="nav-wrapper col s12 red lighten-2">
     
      <ul class="right hide-on-med-and-down">
        <li><a href="login.htm" target="iframeNoob  "><i class="material-icons">message</i></a></li>
        
      </ul>
    </div>
  </nav>
    <!-- Page Layout here -->
    <div class="row">

      
        
      <div class="col s2 grey lighten-3">
        adds
      </div>
        
      <div class="col s10 teal lighten-4">
        <!-- Teal page content  -->
        
        <div class="iframeDiv">
            <iframe name="iframeNoob" class="iframeDivNoob"></iframe>
        </div>
      </div>

        
        
    </div>
        
       
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>
    </body>
</html>
