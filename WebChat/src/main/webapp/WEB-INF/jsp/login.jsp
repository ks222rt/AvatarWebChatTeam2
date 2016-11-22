<%-- 
    Document   : login
    Created on : 2016-nov-14, 11:26:30
    Author     : Kristoffer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
    <head>
        <link href="${pageContext.request.contextPath}/Resources/CSS/style.css" rel="stylesheet" type="text/css" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Avatar - WebChat</title>
    </head>
    <body>

    <main>
    <center>
        <div class="section"></div>
            <h5 class="teal-text " >Avatar - WebChat</h5>
        <div class="section"></div>

        <div class="container">
        <div class="z-depth-5 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">
            <form class="col s12" method="post">
            <div class='row'>
              <div class='col s12'>
              </div>
            </div>
              
            <c:if test="${not empty error_message}">
                <blockquote class="card-panel chip red white-text">
                    Error: ${error_message}
                    <i class="close material-icons">close</i>
                </blockquote>
            </c:if>
              
            <div class='row'>
              <div class='input-field col s12'>
                 <i class="small material-icons teal999" style=" float: left">perm_identity</i>
                <input class='validate' type='text' name='username' id='username' />
                <label for='email'>Enter your username</label>
              </div>
            </div>

            <div class='row'>
              <div class='input-field col s12'>
                 <i class="small material-icons teal999" style=" float: left">https</i>
                <input class='validate' type='password' name='password' id='password' />
                <label for='password'>Enter your password</label>
              </div>
		<a class='pink-text' href='#!'><b>Forgot Password?</a>					
            </div>

            <br />
            <center>
              <div class='row'>
                  <button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect teal '>Login</button>
                  <a class='pink-text' href='main.htm'><b>Fake Login(Redirect)</b></a> 
              </div>
            </center>
            
		<a class='pink-text' href='registration.htm'><b>Create Account</b></a> 
                                                                <br>       
          </form>
        </div>
      </div>   
    </center>
    <div class="section"></div>
    <div class="section"></div>
  </main>
        
        
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>
    </body>
</html>
