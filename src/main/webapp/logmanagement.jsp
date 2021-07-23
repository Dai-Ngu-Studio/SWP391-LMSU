<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LMSU Dashboard</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script src="js/iconpro.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <a class="navbar-brand brand-logo mr-5" href="dashboard.html"><img src="images/LMSU LOGO DASHBOARD.svg"
                                                                               class="mr-2" alt="logo"
                                                                               style="margin-left: 36px;"/></a>
            <a class="navbar-brand brand-logo-mini" href="dashboard.html"><img src="images/LMSU LOGO MINI DASHBOARD.svg"
                                                                               alt="logo"/></a>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                <span class="icon-menu"></span>
            </button>
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                        <img src="images/images/faces/fn2.png" alt="profile" style="border-radius: 50%;"/>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                        <a class="dropdown-item">
                            <i class="ti-settings text-primary"></i> Profile
                        </a>
                        <a class="dropdown-item">
                            <i class="ti-power-off text-primary"></i> Logout
                        </a>
                    </div>
                </li>
            </ul>
            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                    data-toggle="offcanvas">
                <span class="icon-menu"></span>
            </button>
        </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial -->
        <!-- partial:../../partials/_sidebar.html -->
        <jsp:include page="sidebar.jsp"/>

        <%--        <nav class="sidebar sidebar-offcanvas" id="sidebar">--%>
        <%--            <ul class="nav">--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" href="dashboard.jsp">--%>
        <%--                        <i class="icon-grid menu-icon"></i>--%>
        <%--                        <span class="menu-title">Dashboard</span>--%>
        <%--                    </a>--%>
        <%--                </li>--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" href="IndexServlet">--%>
        <%--                        <i class="icon-paper menu-icon"></i>--%>
        <%--                        <span class="menu-title">Library</span>--%>
        <%--                    </a>--%>
        <%--                </li>--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" data-toggle="collapse" href="#user-management" aria-expanded="false"--%>
        <%--                       aria-controls="ui-basic">--%>
        <%--                        <i class="icon-head menu-icon"></i>--%>
        <%--                        <span class="menu-title">Users</span>--%>
        <%--                        <i class="menu-arrow"></i>--%>
        <%--                    </a>--%>
        <%--                    <div class="collapse" id="user-management">--%>
        <%--                        <ul class="nav flex-column sub-menu">--%>
        <%--                            <li class="nav-item"><a class="nav-link" href="ShowMemberServlet">Member</a></li>--%>
        <%--                            <li class="nav-item"><a class="nav-link" href="ShowStaffServlet">Staff</a></li>--%>
        <%--                        </ul>--%>
        <%--                    </div>--%>
        <%--                </li>--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" href="ShowBookServlet">--%>
        <%--                        <i class="icon-book menu-icon"></i>--%>
        <%--                        <span class="menu-title">Books</span>--%>
        <%--                    </a>--%>
        <%--                </li>--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" href="BookRentalManagement.html">--%>
        <%--                        <i class="icon-archive menu-icon"></i>--%>
        <%--                        <span class="menu-title">Book Rental</span>--%>
        <%--                    </a>--%>
        <%--                </li>--%>
        <%--                <li class="nav-item">--%>
        <%--                    <a class="nav-link" href="ShowLogServlet">--%>
        <%--                        <i class="icon-file-subtract menu-icon"></i>--%>
        <%--                        <span class="menu-title">Import Log</span>--%>
        <%--                    </a>--%>
        <%--                </li>--%>
        <%--            </ul>--%>
        <%--        </nav>--%>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Data table</h4>
                        <div class="row">
                            <div class="table-responsive">
                                <table id="order-listing"
                                       class="table table-hover dataTable no-footer"
                                       role="grid"
                                       aria-describedby="order-listing_info">
                                    <thead>
                                    <tr>
                                        <th style="width: 54px;">Imported On</th>
                                        <th style="width: 72px;">Total Quantity</th>
                                        <th style="width: 110px;">Total Imported Times</th>
                                        <th style="width: 64px;">Actions</th>
                                    </tr>

                                    </thead>
                                    <tbody>
                                    <c:set var="logMap" value="${requestScope.LOG_MAP_LIST}"/>
                                    <c:forEach var="keyDate" items="${logMap.keySet()}"
                                               varStatus="counter">
                                        <c:set var="totalQuantity" value="${0}"/>
                                        <c:set var="totalLog" value="${0}"/>
                                        <c:forEach var="log" items="${logMap.get(keyDate)}">
                                            <c:set var="totalQuantity"
                                                   value="${totalQuantity+log.quantity}"/>
                                            <c:set var="totalLog" value="${totalLog+1}"/>
                                        </c:forEach>
                                        <tr>
                                            <td>${keyDate}</td>
                                            <td>${totalQuantity}</td>
                                            <td>${totalLog}</td>
                                            <td>
                                                <button class="btn btn-outline-primary"
                                                        data-toggle="modal"
                                                        data-target="#logModal${counter.count}">View
                                                    Details
                                                </button>
                                                <div class="modal fade"
                                                     id="logModal${counter.count}"
                                                     tabindex="-1"
                                                     role="dialog"
                                                     aria-labelledby="exampleModalLongTitle"
                                                     aria-hidden="true">
                                                    <div class="modal-dialog modal-xl" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title"
                                                                    id="modalTitle${counter.count}">
                                                                    Import Log</h5>
                                                                <button type="button" class="close"
                                                                        data-dismiss="modal"
                                                                        aria-label="Close">
                                                                                        <span
                                                                                                aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <fieldset disabled>
                                                                    <div class="form-group row">
                                                                        <label class="col-sm-3 col-form-label">Imported
                                                                            date</label>
                                                                        <div class="col-sm-9">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${keyDate}">
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        <label class="col-sm-3 col-form-label">Total
                                                                            Quantity</label>
                                                                        <div class="col-sm-9">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${totalQuantity}">
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        <label class="col-sm-3 col-form-label">Total
                                                                            Imports</label>
                                                                        <div class="col-sm-9">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${totalLog}">
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        <div class="table-responsive">
                                                                            <table class="table table-hover">
                                                                                <thead>
                                                                                <tr>
                                                                                    <th scope="col">
                                                                                        Log ID
                                                                                    </th>
                                                                                    <th scope="col">
                                                                                        Book Title
                                                                                    </th>
                                                                                    <th scope="col">
                                                                                        Manager Imported
                                                                                    </th>
                                                                                    <th scope="col">
                                                                                        Supplier
                                                                                    </th>
                                                                                    <th scope="col">
                                                                                        Quantity
                                                                                    </th>
                                                                                </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                <c:forEach var="log"
                                                                                           items="${logMap.get(keyDate)}">
                                                                                    <tr>
                                                                                        <td>${log.logID} </td>
                                                                                        <td>${log.book.title}</td>
                                                                                        <td>${log.manager.name}</td>
                                                                                        <td>${log.supplier}</td>
                                                                                        <td>${log.quantity}</td>
                                                                                    </tr>
                                                                                </c:forEach>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>

                                                                    </div>
                                                                </fieldset>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button"
                                                                        class="btn btn-primary"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- content-wrapper ends -->
            <!-- partial:../../partials/_footer.html -->
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2021.
                            Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin
                                template</a> from BootstrapDash. All rights reserved.</span>
                    <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made
                            with <i class="ti-heart text-danger ml-1"></i></span>
                </div>
            </footer>
            <!-- partial -->
        </div>
        <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>
<!-- container-scroller -->
<!-- plugins:js -->
<script src="vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<%--    Datatable--%>
<link rel="stylesheet" type="text/css"
      href="https://cdn.datatables.net/v/bs4/jqc-1.12.4/dt-1.10.25/datatables.min.css"/>

<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/jqc-1.12.4/dt-1.10.25/datatables.min.js"></script>
<script type="text/javascript" src="js/logmanager.js"></script>
<%--    End inject dataTable--%>
<!-- inject:js -->
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->

<!-- End custom js for this page-->
</body>
</html>
