<!DOCTYPE html>
<%@ page language="java" import="cs5530.*" %>
<html lang="en">
  <head>
      <!-- Bootstrap -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>


      <!-- MDL -->
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
      <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
      <script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

      <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Signin Template for Bootstrap</title>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="signin.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

  <div class="container">
      <div class="jumbotron">
          <h1>UTrack</h1>
      </div>

    <div class="container">

      <form class="form-signin" method="post" action="index.jsp">
        <h2 class="form-signin-heading">Please sign in</h2>

          <!-- Theme labels -->
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <input name="email-input" class="mdl-textfield__input" type="text" id="username">
              <label class="mdl-textfield__label input_label" for="username">Username</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <input name="pwd-input" type="password" class="mdl-textfield__input" type="text" id="password">
              <label class="mdl-textfield__label input_label" for="password">Password</label>
          </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
          <button class="btn-lg mdl-button btn-theme btn-block btn-primary mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
              Sign in
          </button>
          <!--<button class="btn btn-theme btn-lg btn-primary btn-block" type="submit">Sign in</button> -->
      </form>

    </div> <!-- /container -->

  <%
  Connector connector = new Connector();
  User user = new User();

  String email = request.getParameter("email-input");
  String password = request.getParameter("pwd-input");
  if( email != null && password != null){
  %>

      <%
      if (user.loginUser(email, password, connector.stmt)){
      %>

      <jsp:forward page="orders.jsp"/>

      User: <b><%=email%></b>, is successfully logged in!
      <%
      } else {%>
    <div class="form-signin">
      Incorrect username or password, please try again!
    </div>
      <%
      }
      %>
  <%
  }
  %>
  </div>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>