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
         <div class="row">
         
<div class="col s3 red lighten-2">
    
    <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar2.png" alt="Contact Person">
    Jane Doe
    
  </div>
      
    
    </div>
    <div class="col s9 red lighten-2">
        <center>
        <h5 class="white-text">Avatar - WebChat</h5>
    </center>
  </div>
      
    
    
    
    
    
    
  
    <!-- Page Layout here -->
    

      
        
      <div class="col s3 grey lighten-3">
     
        
        <ul class="collapsible" data-collapsible="accordion">
  <li>
    <div class="collapsible-header "><span class="new badge">4</span><i class="material-icons red999">chat</i>Message</div>
    <div class="collapsible-body">
        <a href="#!" class="collection-item"><span class="badge">1</span>Alan</a>
        <br>
        <a href="#!" class="collection-item"><span class="badge">2</span>Steven</a>
        <br>
        <a href="#!" class="collection-item"><span class="badge">1</span>Sasha</a>
    </div>
  </li>
  <li>
    <div class="collapsible-header"><span class="badge">4</span><i class="material-icons red999">supervisor_account</i>Friends Online</div>
    <div class="collapsible-body">
    <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar3.png" alt="Contact Person">
    Sasha
  </div>
    <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar4.png" alt="Contact Person">
    Steven
  </div>
    <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar5.png" alt="Contact Person">
    Albert
  </div>
    <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar6.png" alt="Contact Person">
    Cho
  </div>
    
    </div>
  </li>
  
   <li>
    <div class="collapsible-header"><span class="badge">2</span><i class="material-icons red999">perm_identity</i>Friend Request</div>
    <div class="collapsible-body">
    
    <ul class="collection">
    <li class="collection-item avatar">
      <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar7.png" alt="" class="circle">
      <span class="title">Bill</span>
      <p>Name: Bill Andersson<br>
         From: Canada
      </p>
      <a href="#!"><i class="material-icons">thumb_up</i></a>
      <a href="#!"><i class="material-icons">thumb_down</i></a>
    
    </li>
    <li class="collection-item avatar">
      <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar8.png" alt="" class="circle">
      <span class="title">Doggy</span>
      <p>Name: Snoop Dogg<br>
         From: USA
      </p>
      <a href="#!"><i class="material-icons">thumb_up</i></a>
      <a href="#!"><i class="material-icons">thumb_down</i></a>
    
    </li>
    
    </ul>
    
    </div>
  </li>
  
</ul> 
      
      <div class="search-wrapper">
      <div class="col s12">
      <div class="row">
        <div class="input-field col s12 ">
          <i class="material-icons prefix red999">search</i>
          <input type="text" id="autocomplete-input" class="autocomplete ">
          <label for="autocomplete-input" >Search User</label>
        </div>
      </div>
    </div>
    </div>
      
    <div class="util">
        <i class="material-icons red999">settings</i> <i class="material-icons red999">power_settings_new</i>
    </div>
      </div>
        
      <div class="col s9 grey lighten-4">
        <!-- Teal page content  -->
        
        <div class="iframeDiv">
            <iframe name="iframeNoob" class="iframeDivNoob"></iframe>
        </div>
      </div>

        
        
    </div>
</div>
        
       
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>
    </body>
</html>
