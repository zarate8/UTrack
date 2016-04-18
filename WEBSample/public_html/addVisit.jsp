<!DOCTYPE html>
<%@ page language="java" import="cs5530.*" %>
<html lang="en">
<head>


    <!-- Bootstrap -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <!-- Include Required Prerequisites -->
    <script type="text/javascript" src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap/latest/css/bootstrap.css" />

    <!-- Include Date Range Picker -->
    <script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <!-- Bootstrap -->
    <!-- Optional theme -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>

    <!--End Bootstrap -->

    <!--Date Picker-->
    <script src="js/jquery-1.7.1.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>

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

    <%
    Connector connector = (Connector)session.getAttribute("connector");
    User user = (User)session.getAttribute("user");
    POI poi = (POI)session.getAttribute("poi");
    %>

    <script language="JavaScript">
    $(function() {
    $('input[name="birthdate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true
    });
    });

    $(function () {
        $('#datetimepicker1').datetimepicker();
    });
    </script>

</head>

<body>

<div class="container">
    <div class="jumbotron">
        <h1>Utrack</h1>
    </div>

    <div class="container">

        <div class="container">
            <div class="row">
                <div class='col-sm-6'>
                    <div class="form-group">
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!--<form class="form-signin" method="post" action="index.jsp">-->
            <!--<form method=post action="index.jsp#addVisit">-->
                <h2 class="form-signin-heading">New visit</h2>

                <div class="mdl-layout__tab-panel" id="addVisit">

                    <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">

                        <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
                            <div class="mdl-card mdl-cell mdl-cell--9-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">

                                <div class="mdl-card__supporting-text">

                                    <!--POI Selection-->
                                    <div class="mdl-textfield">
                                        <div>
                                            <label class="input_label mdl-color-text--red-900">Select POI</label>
                                        </div>
                                        <input list="pois" name="poi">
                                        <datalist id="pois">
                                            <%
                                            for(String s : poi.getPOIList(connector.stmt)){
                                            out.println("<option value=\"" + s + "\">");
                                            }
                                            %>
                                        </datalist>
                                    </div>

                                    <div>
                                    <!--Cost-->
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                            <input name="cost-in" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="cost">
                                            <label class="mdl-textfield__label input_label mdl-color-text--red-900" for="cost">Cost</label>
                                            <span class="mdl-textfield__error">Input must be a number</span>
                                        </div>
                                    </div>

                                    <div>
                                    <!--Party Count-->
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input name="partyCount-in" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="partyCount">
                                        <label class="mdl-textfield__label input_label mdl-color-text--red-900" for="partyCount">Number of people</label>
                                        <span class="mdl-textfield__error">Input must be a number</span>
                                    </div>
                                    </div>
                                    <div>
                                    <!--Date-->
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input class="mdl-textfield__input" type="text" name="birthdate"/>
                                    </div>
                                    </div>
                                        <%
                                        String p = request.getParameter("poi");
                                        String cost = request.getParameter("cost-in");
                                        String partyCount = request.getParameter("partyCount-in");
                                        String date = request.getParameter("date-in");

                                        try {
                                            int c = Integer.parseInt(cost);
                                            int pc = Integer.parseInt(partyCount);

                                            if(p != null && cost != null && partyCount != null && date != null)
                                            {
                                                int pid = user.addVisit(p, c, pc, date, connector.stmt, connector._con);
                                            }
                                        } catch(Exception e) {

                                        } finally {

                                        }
                                    %>
                                    <div>
                                    <button type="button" class="btn-sm mdl-button btn-theme btn-primary mdl-js-button
                                                mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900"
                                                  data-toggle="modal" data-target="#myModal">Add Visit</button>
                                        <form method=get action="addVisit.jsp">
                                        <button type="button" class="btn-sm mdl-button btn-theme btn-primary mdl-js-button
                                                mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900"
                                                >Add Visit</button>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <!-- Modal -->
                        <div class="modal fade" id="myModal" role="dialog">
                            <div class="modal-dialog">

                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title">Added Visits</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Some text in the modal.</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </section>

                </div>
            <!--</form>-->

        <%
        String username = request.getParameter("username-input");
        String password = request.getParameter("pwd-input");

        if(username != null && password != null){
            if (user.loginUser(username, password, connector.stmt)){
                session.setAttribute("username", username);
                session.setAttribute("signedIn", "true");
        %>
                <script>window.location.href='index.jsp';</script>
        <%
            } else {%>
            <div class="form-signin">
                Incorrect username or password, please try again!
            </div>
        <%}}%>

    </div> <!-- /container -->
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
