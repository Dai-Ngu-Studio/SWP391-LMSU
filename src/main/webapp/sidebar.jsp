<%--
  Created by IntelliJ IDEA.
  User: bchao
  Date: 19/06/2021
  Time: 1:49 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Required meta tags --%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%-- plugins:css --%>
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <%-- endinject --%>
    <%-- Plugin css for this page --%>
    <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <%-- End plugin css for this page --%>
    <%-- inject:css --%>
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <%-- endinject --%>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <script src="js/iconpro.js"></script>
</head>
<body>
<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="dashboard.jsp">
                <i class="icon-grid menu-icon"></i>
                <span class="menu-title">Dashboard</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="IndexServlet">
                <i class="icon-paper menu-icon"></i>
                <span class="menu-title">Library</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#user-management" aria-expanded="false"
               aria-controls="ui-basic">
                <i class="icon-head menu-icon"></i>
                <span class="menu-title">Users</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="user-management">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item"><a class="nav-link" href="ShowMemberServlet">Member</a></li>
                    <li class="nav-item"><a class="nav-link" href="ShowStaffServlet">Staff</a></li>
                </ul>
            </div>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="ShowBookServlet">
                <i class="icon-book menu-icon"></i>
                <span class="menu-title">Books</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="ShowAuthorServlet">
                <i class="fal fa-user-edit menu-icon"></i>
                <span class="menu-title">Authors</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="ShowDirectOrderServlet">
                <i class="icon-archive menu-icon"></i>
                <span class="menu-title">Book Rental</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="ShowLogServlet">
                <i class="icon-file-subtract menu-icon"></i>
                <span class="menu-title">Import Log</span>
            </a>
        </li>
    </ul>
</nav>
</body>
</html>
