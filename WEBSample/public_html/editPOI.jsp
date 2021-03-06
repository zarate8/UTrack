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

    <title>Edit POI</title>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="signin.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="jquery-1.12.0.min.js"></script>

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

    };

    function back(){
        window.location("index.jsp");
        alert("here");
    }
    </script>

</head>

<body>

<div class="container">
    <div class="jumbotron">
        <h1>Utrack</h1>

        <%
        Object isSuccessful = session.getAttribute("isAddPOI");


        if(isSuccessful != null && isSuccessful.toString().equals("true")){
            session.setAttribute("isAddPOI", "false");
        %>
        <h4>POI Successfully added</h4>
        <%}%>
    </div>

    <div class="container">

        <div class="container">

        </div>
        <!--<form class="form-signin" method="post" action="index.jsp">-->
            <form method=post action="editPOI.jsp">
                <%
                    EditPOI ep = (EditPOI)session.getAttribute("EditPOI");
                    String n = "";
                    String cat = "";
                    String add = "";
                    String url = "";
                    String pnum = "";
                    int y = 0;
                    String hr = "";
                    int pr = 0;
                    out.println(ep);
                    if(ep!=null){
                        n += ep.name;
                        cat = ep.category;
                        add = ep.address;
                        url = ep.URL;
                        pnum = ep.tel_num;
                        y = ep.yr_est;
                        hr = ep.hours;
                        pr = ep.price;
                    }
                %>
                <h2 class="form-signin-heading">Edit POI <%out.println(ep.name);%></h2>

                <div class="mdl-layout__tab-panel" id="addVisit">

                    <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">

                        <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
                            <div class="mdl-card mdl-cell mdl-cell--9-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">

                                <div class="mdl-card__supporting-text">

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(n);%>" name="Name" class="mdl-textfield__input" type="text" id="Name">

                                        <label class="mdl-textfield__label input_label" for="Name">Name</label>
                                    </div>

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(cat);%>" name="Category" class="mdl-textfield__input" type="text" id="Category">
                                        <label class="mdl-textfield__label input_label" for="Category">Category</label>
                                    </div>

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(add);%>" name="Address" class="mdl-textfield__input" type="text" id="Address">
                                        <label class="mdl-textfield__label input_label" for="Address">Address</label>
                                    </div>

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(url);%>" name="URL" class="mdl-textfield__input" type="text" id="URL">
                                        <label class="mdl-textfield__label input_label" for="URL">URL</label>
                                    </div>

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(pnum);%>" name="phoneNumber" class="mdl-textfield__input" type="text" id="phoneNumber">
                                        <label class="mdl-textfield__label input_label" for="phoneNumber">Phone Number</label>
                                    </div>
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(y);%>" name="year" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="year">
                                        <label class="mdl-textfield__label input_label mdl-color-text--red-900" for="year">Year</label>
                                        <span class="mdl-textfield__error">Input must be a number</span>
                                    </div>
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(hr);%>" name="hours" class="mdl-textfield__input" type="text" id="hours">
                                        <label class="mdl-textfield__label input_label" for="hours">hours</label>
                                    </div>

                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                        <input value="<%out.print(pr);%>" name="price" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="price">
                                        <label class="mdl-textfield__label input_label mdl-color-text--red-900" for="price">Price Per Person</label>
                                        <span class="mdl-textfield__error">Input must be a number</span>
                                    </div>

                                    <%


                                    %>

                                <%
                                    //EditPOI ep = new EditPOI();
                                    String Name = request.getParameter("Name");
                                    String Category = request.getParameter("Category");
                                    String Address = request.getParameter("Address");
                                    String URL = request.getParameter("URL");
                                    String phoneNumber = request.getParameter("phoneNumber");
                                    String year = request.getParameter("year");
                                    String hours = request.getParameter("hours");
                                    String price = request.getParameter("price");


                                    int cost = -1;
                                    int yr = -1;
                                    try {
                                    out.println(price + " " + year);
                                        cost = Integer.parseInt(price);
                                        yr = Integer.parseInt(year);

                                    } catch(Exception e) {
                                        out.println("Not Worked" + e);

                                    } finally {

                                    }

                                    if(Name != null && Category!= null && Address != null && URL != null &&
                                    phoneNumber != null && year != null && hours != null && price != null &&
                                    Name != "" && Category!= "" && Address != "" && URL != "" &&
                                    phoneNumber != "" && year != "" && hours != "" && price != "")
                                    {
                                        String setQuery = "";
                                        setQuery += "name = '" + Name + "', ";

                                        setQuery += "category = '" + Category + "', ";

                                        setQuery += "address = '" + Address + "', ";

                                        setQuery += "URL = '" + URL + "', ";

                                        setQuery += "tel_num = '" + phoneNumber + "', ";

                                        setQuery += "yr_est = '" + year + "', ";

                                        setQuery += "hours = '" + hours + "', ";

                                        setQuery += "price = '" + price + "'";

                                        poi.updatePOI(Name, setQuery, connector._con, connector.stmt);

                                        //boolean t = poi.addPOI(Name, Category, Address, URL, phoneNumber, yr, hours, cost,
                                        //connector._con, connector.stmt);

                                        //if(t){
                                    %>
                                            <script>alert("POI Successfully added");</script>
                                    <%
                                        //}
                                        session.setAttribute("isAddPOI", "true");
                                    }
                                %>

                                </div>
                            <div>
                                <!--<button type="button" class="btn-sm mdl-button btn-theme btn-primary mdl-js-button-->
                                                <!--mdl-button&#45;&#45;raised mdl-js-ripple-effect mdl-button&#45;&#45;accent mdl-color&#45;&#45;red-900"-->
                                        <!--data-toggle="modal" data-target="#myModal">Add POI</button>-->
                                <button type="submit" class="btn-sm mdl-button btn-theme btn-primary mdl-js-button
                                                mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900"
                                        >Add POI</button>
                                <!--<input type="submit" value="Add POI" class="btn-lg mdl-button btn-theme btn-block btn-primary mdl-js-button mdl-button&#45;&#45;raised mdl-js-ripple-effect mdl-button&#45;&#45;accent">-->

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
                                        <p>POI Successfully Added</p>
                                    </div>
                                    <div class="modal-footer">
                                        <!--<button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>-->
                                        <input type="submit" class="btn btn-default" data-dismiss="modal">Close</input>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </section>

                </div>
            </form>
        <input value="Done" onclick="window.location = 'index.jsp';" class="btn-sm mdl-button btn-theme btn-primary mdl-js-button
                                                mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-color--red-900"/>
    </div> <!-- /container -->
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
