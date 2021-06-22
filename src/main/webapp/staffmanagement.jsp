<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: NDungx
  Date: 6/9/2021
  Time: 12:29 PM
--%>
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
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
</head>
<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <a class="navbar-brand brand-logo mr-5" href="dashboard.jsp"><img src="images/LMSU LOGO DASHBOARD.svg"
                                                                              class="mr-2" alt="logo"
                                                                              style="margin-left: 36px;"/></a>
            <a class="navbar-brand brand-logo-mini" href="dashboard.jsp"><img src="images/LMSU LOGO MINI DASHBOARD.svg"
                                                                              alt="logo"/></a>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                <span class="icon-menu"></span>
            </button>
            <ul class="navbar-nav mr-lg-2">
                <li class="nav-item nav-search d-none d-lg-block">
                    <div class="input-group">
                        <div class="input-group-prepend hover-cursor" id="navbar-search-icon">
                                <span class="input-group-text" id="search">
                                    <i class="icon-search"></i>
                                </span>
                        </div>
                        <input type="text" class="form-control" id="navbar-search-input" placeholder="Search now"
                               aria-label="search" aria-describedby="search">
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item dropdown">
                    <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#"
                       data-toggle="dropdown">
                        <i class="icon-bell mx-0"></i>
                        <span class="count"></span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                         aria-labelledby="notificationDropdown">
                        <p class="mb-0 font-weight-normal float-left dropdown-header">Notifications</p>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-success">
                                    <i class="ti-info-alt mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">Application Error</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    Just now
                                </p>
                            </div>
                        </a>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-warning">
                                    <i class="ti-settings mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">Settings</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    Private message
                                </p>
                            </div>
                        </a>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-info">
                                    <i class="ti-user mx-0"></i>
                                </div>
                            </div>
                            <div class="preview-item-content">
                                <h6 class="preview-subject font-weight-normal">New user registration</h6>
                                <p class="font-weight-light small-text mb-0 text-muted">
                                    2 days ago
                                </p>
                            </div>
                        </a>
                    </div>
                </li>
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                        <img src="images/images/faces/fn2.png" alt="profile" style="border-radius: 50%;"/>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
                        <a class="dropdown-item" href="ShowProfileServlet">
                            <i class="ti-settings text-primary"></i> Profile
                        </a>
                        <a class="dropdown-item" href="LogoutServlet">
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
<%--                    <a class="nav-link" href="index.jsp">--%>
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
<%--                    <a class="nav-link" href="ImportLog.html">--%>
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
                            <div class="col-12">
                                <div class="table-responsive">
                                    <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-6">
                                                <div class="dataTables_length" id="order-listing_length"><label>Show
                                                    <select name="order-listing_length"
                                                            aria-controls="order-listing"
                                                            class="custom-select custom-select-sm form-control">
                                                        <option value="5">5</option>
                                                        <option value="10">10</option>
                                                        <option value="15">15</option>
                                                        <option value="-1">All</option>
                                                    </select> entries</label></div>
                                            </div>
                                            <div class="col-sm-12 col-md-6">
                                                <div id="order-listing_filter" class="dataTables_filter">
                                                    <form action="DispatchServlet">
                                                        <input type="search" class="form-control"
                                                               id="searchBox" placeholder="Search"
                                                               name="txtSearchValue" value="${param.txtSearchValue}"
                                                               aria-controls="order-listing"
                                                        >
                                                        <button class="btn btn-primary" type="submit"
                                                                name="btAction" value="SearchStaff"
                                                                style="border-radius: 5px"><i class="fa fa-search"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <table id="order-listing" class="table dataTable no-footer" role="grid"
                                                       aria-describedby="order-listing_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc text-right" tabindex="0"
                                                            aria-controls="order-listing" rowspan="1" colspan="1"
                                                            aria-sort="ascending"
                                                            aria-label="Order #: activate to sort column descending"
                                                            style="width: 0px;">#
                                                        </th>
                                                        <th class="sorting text-left" aria-controls="order-listing"
                                                            rowspan="1" colspan="1" tabindex="0"
                                                            aria-label="MEMBER ID: activate to sort column ascending"
                                                            style="width: 96px;">MEMBER ID
                                                        </th>
                                                        <th class="sorting text-left" aria-controls="order-listing"
                                                            rowspan="1" colspan="1" tabindex="0"
                                                            aria-label="NAME: activate to sort column ascending"
                                                            style="width: 56px;">NAME
                                                        </th>
                                                        <th class="sorting text-left" aria-controls="order-listing"
                                                            rowspan="1" colspan="1" tabindex="0"
                                                            aria-label="EMAIL: activate to sort column ascending"
                                                            style="width: 67px;">EMAIL
                                                        </th>
                                                        <th class="sorting text-center" aria-controls="order-listing"
                                                            rowspan="1" colspan="1" tabindex="0"
                                                            aria-label="ROLE: activate to sort column ascending"
                                                            style="width: 56px;">STATUS
                                                        </th>
                                                        <th class="sorting text-center" aria-controls="order-listing"
                                                            rowspan="1" colspan="1" tabindex="0"
                                                            aria-label="Actions: activate to sort column ascending"
                                                            style="width: 64px;">INFO
                                                        </th>

                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:set var="staffList" value="${requestScope.STAFF_LIST}"/>

                                                    <c:forEach var="staff" items="${staffList}" varStatus="counter">
                                                        <c:if test="${staff.roleID eq '2' or staff.roleID eq '3'}">
                                                            <tr class="odd">
                                                                <td class="sorting_1 text-right">${counter.count}</td>
                                                                <td class="text-left">${staff.id}</td>
                                                                <td class="text-left">${staff.name}</td>
                                                                <td class="text-left">${staff.email}</td>
                                                                <td class="text-center">
                                                                    <span class="badge badge-success text-center">Active</span>
                                                                </td>

                                                                <td class="text-center">
                                                                    <button data-toggle="modal" data-target="#logModal1"
                                                                            class="btn btn-outline-primary text-center">View
                                                                    </button>
                                                                    <div class="modal fade" id="logModal1" tabindex="-1"
                                                                         role="dialog" aria-labelledby="exampleModalLongTitle"
                                                                         aria-hidden="true">
                                                                        <div class="modal-dialog" role="document">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h5 class="modal-title"
                                                                                        id="exampleModalLongTitle">
                                                                                        View Staff Details
                                                                                    </h5>
                                                                                    <button type="button" class="close"
                                                                                            data-dismiss="modal"
                                                                                            aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span>
                                                                                    </button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <form>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">Avatar
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <img class="img-thumbnail rounded float-right"
                                                                                                     style="height: 200px; width: auto;"
                                                                                                     src="${pageContext.request.contextPath}/image/${staff.profilePicturePath}"
                                                                                                     id="coverPictureUpdate${staff.id}"
                                                                                                     alt="Avatar"
                                                                                                     onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"
                                                                                                />
                                                                                                <input type="hidden"
                                                                                                       name="txtCoverFile"
                                                                                                       value="${staff.profilePicturePath}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">Email</label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text" readonly
                                                                                                       class="form-control"
                                                                                                       value="${staff.email}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">Name
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text" readonly
                                                                                                       class="form-control"
                                                                                                       value="${staff.name}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">Semester
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text" readonly
                                                                                                       class="form-control"
                                                                                                       value="${staff.semester}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">Role
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text" readonly
                                                                                                       class="form-control"
                                                                                                       value="${staff.roleID}">
                                                                                            </div>
                                                                                        </div>
                                                                                    </form>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button"
                                                                                            class="btn btn-outline-primary"
                                                                                            data-dismiss="modal">Close
                                                                                    </button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12 col-md-5">
                                                <div class="dataTables_info" id="order-listing_info" role="status"
                                                     aria-live="polite">Showing 1 to 5 of 10 entries
                                                </div>
                                            </div>
                                            <div class="col-sm-12 col-md-7">
                                                <div class="dataTables_paginate paging_simple_numbers"
                                                     id="order-listing_paginate">
                                                    <ul class="pagination">
                                                        <li class="paginate_button page-item previous disabled"
                                                            id="order-listing_previous"><a href="#"
                                                                                           aria-controls="order-listing"
                                                                                           data-dt-idx="0" tabindex="0"
                                                                                           class="page-link">Previous</a>
                                                        </li>
                                                        <li class="paginate_button page-item active"><a href="#"
                                                                                                        aria-controls="order-listing"
                                                                                                        data-dt-idx="1"
                                                                                                        tabindex="0"
                                                                                                        class="page-link">1</a>
                                                        </li>
                                                        <li class="paginate_button page-item "><a href="#"
                                                                                                  aria-controls="order-listing"
                                                                                                  data-dt-idx="2"
                                                                                                  tabindex="0"
                                                                                                  class="page-link">2</a>
                                                        </li>
                                                        <li class="paginate_button page-item next"
                                                            id="order-listing_next"><a href="#"
                                                                                       aria-controls="order-listing"
                                                                                       data-dt-idx="3" tabindex="0"
                                                                                       class="page-link">Next</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
<!-- Plugin js for this page -->
<script src="vendors/datatables.net/jquery.dataTables.js"></script>
<script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
<!-- End plugin js for this page -->
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
