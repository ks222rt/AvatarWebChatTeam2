<%-- 
    Document   : header
    Created on : 2016-nov-21, 16:15:58
    Author     : sundi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.request.contextPath}/Resources/CSS/style.css" rel="stylesheet" type="text/css" >
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.1.1.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avatar - WebChat</title>
    </head>
    <body>
        
        
        <nav class="pink" id="navBar">
    Menu
    <a href="#!" class="center brand-logo"><i class="material-icons">cloud</i>Avatar - WebChat</a>
    
    <ul id="slide-out" class="side-nav ">
        
    <li><div class="userView">
      <div class="chip">
    <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar2.png" alt="Contact Person">
    ${user.getFirstname()} ${user.getLastname()}  
    </div>
      
    </div></li>
    
    
    <ul  class="collapsible" data-collapsible="accordion" id="innerNav">
  <li>
    <div class="collapsible-header "><span class="new badge">3</span><i class="material-icons red999">chat</i>Message</div>
    <div class="collapsible-body">
        <a href="#!" class="collection-item">Alan<span class="badge">1</span></a>
        <br>
        <a href="#!" class="collection-item">Steven<span class="badge">2</span></a>
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
      
      <div class="accept">
      <i class="left material-icons red999">thumb_up</i>
      <i class="right material-icons red999">thumb_down</i>
      </div>
    </li>
    <li class="collection-item avatar">
      <img src="${pageContext.request.contextPath}/Resources/AVATAR/avatar8.png" alt="" class="circle">
      <span class="title">Doggy</span>
      <p>Name: Snoop Dogg<br>
         From: USA
      </p>
      <div class="accept">
      <i class="left material-icons red999">thumb_up</i>
      <i class="right material-icons red999">thumb_down</i>
      </div>
    </li>
    
    </ul>
    
    </div>
  </li>
  

  
  <div class="util">
      <i class="left material-icons red999">settings</i>
      
        <a href="<c:url value="/main/logout.htm" />Logout</a>
        <i class="material-icons red999">power_settings_new</i></button>
      
      </div>
  
</ul> 
 
     
      
     
    
  </ul>
  <a href="#" data-activates="slide-out" class="button-collapse show-on-large"><i class="material-icons">menu</i></a>
              
</nav>
    </body>
</html>
