<!DOCTYPE html>
<%@ page language="java" import="cs5530.*, java.util.*" %>
<html lang="en">
<head>


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Utrack</title>

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
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2css/bootstrap-theme.min.css">
    <!-- Latest compiled and minified JavaScript -->
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>

    <!--End Bootstrap -->

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="images/android-desktop.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <link rel="shortcut icon" href="images/favicon.png">

    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.min.css">
    <link rel="stylesheet" href="styles.css">

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
    <script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
    <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>

    <!-- Custom styles for this template -->
    <link href="signin.css" rel="stylesheet">
    <link href="styles.css" rel="stylesheet">
    <script src="jquery-1.12.0.min.js"></script>

    <%
    Connector connector = (Connector)session.getAttribute("connector");
    User user = (User)session.getAttribute("user");
    POI poi = (POI)session.getAttribute("poi");
    %>

    <script type="text/javascript">

    function mostUseful(){
        <!--alert("I'm being useful " + arguments[0]);-->

        var lim = document.getElementById("amount").value;

        if(lim == ""){
            lim = "10";
        }

        $.get('browseJSP.jsp', {usefulFeedback : "true",
                                pname : arguments[0],
                                limit : lim},
                   function (output)
                   {
                        $('#feedbackResults').html(output).show();
                   });
    }

    function userTrust(){

        var trust = $( "#trust option:selected" ).text();

        var uname = getUsername(arguments[0]);

        var results = "#" + arguments[2] + "";

        alert(uname + ", Res = " + results + ", Trust = "+ trust);
        alert("poi = " + arguments[1] + "username = " + arguments[0]  + " option =" + arguments[2]);
        $.get('browseJSP.jsp', {setTrust : "true",
                                theirUname : uname,
                                trust : trust},
                   function (output)
                   {
                        $(results).html(output).show();
                   });
    }



    function rateFeedback(){
        var option = $( "#rating option:selected" ).text();

        var uname = getUsername(arguments[0]);

        var resultHolder = arguments[2];
        GetRequest(uname, arguments[1], option, resultHolder);

    }

    function GetRequest(){

            var results = "#" + arguments[3] + "";
            alert(results);
            alert("poi = " + arguments[1] + "username = " + arguments[0]  + " option =" + arguments[2]);
            $.get('browseJSP.jsp', {rateFeedback : "true",
                                    theirUname : arguments[0],
                                    pname : arguments[1],
                                    rating : arguments[2]},
                       function (output)
                       {
                            $(results).html(output).show();
                       });
    }


    function getUsername(){
        var n = arguments[0].match(/Username:(.*)\,(.*)POI/);

        return removeWhiteSpace(n);
    }

    function removeWhiteSpace(){
        var n = arguments[0];

        var pn = "";
        if(n != null){
            pn = n[1];

            var i;
            var start;
            var end;

            for(i = 0; i < pn.length; i++){
                if(pn.charAt(i) != ' ' && pn.charAt(i) != ','){
                    start = i;
                    break;
                }
            }

            for(i = pn.length - 1; i >= 0; i--){
                if(pn.charAt(i) != ' ' && pn.charAt(i) != ','){
                    end = i;
                    break;
                }
            }

            pn = pn.substring(start, end+1);
        }
        return pn;
    }
    </script>

</head>

<body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">

<div class="jumbotron">
    <h1 onclick="goHome()">Utrack</h1>
    <%
    String query = "";
    %>
</div>
<form name="form">
    <div class="mdl-layout__tab-panel" id="browsePOI">
        <div class="android-be-together-section mdl-typography--text-center mdl-text--grey-900">
            <b><h2 id="here">Feedback</h2></b>
        </div>
        <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
            <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
                <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">

                    <div class="mdl-card__supporting-text">
                        <% String poiname = (String)session.getAttribute("POIFeedback");%>

                        <h4><%out.println(poiname);%></h4>
                        <%
                        HashMap<String, String> mp = poi.getPOI(poiname, connector.stmt);
                        Iterator it = mp.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            out.println("<div><b>" + pair.getKey() + " : " + pair.getValue() + "</b></div>");
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                        %>
                    </div>
                    <div class="mdl-card__supporting-text">
                        <div style="width:100px;"  class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <input name="amount" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="amount">
                            <label class="mdl-textfield__label input_label mdl-color-text--red-900" for="amount">Amount</label>
                            <span class="mdl-textfield__error">Input must be a number</span>
                        </div>
                        <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="checkbox-1">
                            <%
                            out.print(
                            "<input onclick=\"mostUseful('"+ poiname +"')\" type=\"checkbox\" id=\"checkbox-1\" class=\"mdl-checkbox__input\">");
                            %>
                            <span class="mdl-checkbox__label">Show most useful feedback</span>
                        </label>

                    </div>
                </div>


            </div>
        </section>
    </div>
    <div class="mdl-layout mdl-js-layout">
        <main class="mdl-layout__content">
            <div id="feedbackResults" class="mdl-cell mdl-cell--12-col">
                <%
                ArrayList<String> popPOI = poi.getFeedbackRecordsArr(poiname, connector.stmt, connector._con);

                int size = popPOI.size();

                String[] pois = new String[size];
                for(int i = 0; i < size; i++){
                    String[] str = popPOI.get(i).split("\\n");
                    String p = popPOI.get(i);

                    p ="word";

                    out.println(
                        "<section class=\"section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp\">" +
                        "<div class=\"mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone\">" +
                        "<div class=\"mdl-card__supporting-text\">" +
                            "<b>" + popPOI.get(i) + "</b>" +
                        "</div>" +
                            "<div class=\"mdl-card__actions\">" +

                                "<div id=\"uresults" + i +"\">" +
                                "<select id=\"rating\">\n" +
                                    "<option value=\"useless\">Useless</option>" +
                                    "<option value=\"useful\">Useful</option>" +
                                    "<option value=\"veryUseful\">Very Useful</option>" +
                                "</select>" +

                                "<input type=\"button\" onclick=\"rateFeedback('hey:hello " + str[0] +"', '" + poiname + "', 'uresults"+ i +"')\"" +
                                "class=\"btn-sm mdl-button btn-theme mdl-js-button\n" +
                                "mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900 \"" +
                                "value=\"Rate\"/>" +
                                "</div>" +

                                "<div id=\"tresults" + i +"\">" +
                                "<select id=\"trust\">\n" +
                                    "<option value=\"Trusted\">Trusted</option>" +
                                    "<option value=\"NotUntrusted\">Not Trusted</option>" +
                                "</select>" +

                                "<input type=\"button\" onclick=\"userTrust('hey:hello " + str[0] +"', '" + poiname + "', 'tresults"+ i +"')\"" +
                                "class=\"btn-sm mdl-button btn-theme mdl-js-button\n" +
                                "mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900 \"" +
                                "value=\"Set\"/>" +
                                "</div>" +


                            "</div>" +
                        "</div>" +
                    "</section>");
                }
                %>
            </div>
        </main>
    </div>


</form>
</body>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
