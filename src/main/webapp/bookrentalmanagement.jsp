<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  Date: 19/06/2021
  Time: 1:46 am
--%>
<jsp:directive.page pageEncoding="UTF-8"/>
<jsp:directive.page contentType="text/html; charset=UTF-8" language="java"/>
<html>
<head>
    <%-- Required meta tags --%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>LMSU Dashboard</title>
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
    <div class="container-fluid page-body-wrapper">
        <%-- sidebar --%>
        <jsp:include page="sidebar.jsp"></jsp:include>
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Orders</h4>
                        <div class="row">
                            <div class="col-12">
                                <div class="table-responsive">
                                    <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-4">
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
                                            <div class="col-sm-12 col-md-8">

                                                <div id="order-listing_filter" class="dataTables_filter">
                                                    <!--Start: Add Order Form-->
                                                    <form action="DispatchServlet" enctype="multipart/form-data"
                                                          method="POST">
                                                        <input type="search" class="form-control"
                                                               placeholder="Search"
                                                               name="txtSearchValue" value="${param.txtSearchValue}"
                                                               aria-controls="order-listing"
                                                               id="searchBox">
                                                        <button class="btn btn-primary" type="submit"
                                                                name="btAction" value="SearchDirectOrder"
                                                                style="border-radius: 5px"><i class="fa fa-search"></i>
                                                        </button>

                                                        <button class="btn btn-primary" type="button"
                                                                style="border-radius: 5px"
                                                                data-toggle="modal"
                                                                data-target="#AddDirectOrderModal"
                                                                title="Add a Direct Order">
                                                            <i class="fa fa-plus"></i></button>

                                                        <div class="modal fade" id="AddDirectOrderModal"
                                                             tabindex="-1"
                                                             role="dialog"
                                                             aria-hidden="true">
                                                            <div class="modal-dialog" role="document">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title">
                                                                            Add a Direct Order
                                                                        </h5>
                                                                        <button type="button"
                                                                                class="close"
                                                                                data-dismiss="modal"
                                                                                aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body" id="addModalBody">
                                                                        <div class="form-group row">
                                                                            <label class="col-sm-3 col-form-label">
                                                                                To-do
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="submit"
                                                                                class="btn btn-primary"
                                                                                name="btAction"
                                                                                value="AddDirectOrder"
                                                                        >
                                                                            Save
                                                                        </button>
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                    <!--End: Add Order Form-->
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <table id="order-listing" class="table dataTable no-footer my-2"
                                                       role="grid"
                                                       aria-describedby="order-listing_info">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="sorting_asc" tabindex="0"
                                                            aria-controls="order-listing" rowspan="1" colspan="1"
                                                            aria-sort="ascending"
                                                            aria-label="Order #: activate to sort column descending"
                                                            style="width: 0px; text-align: center">#
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="order NAME: activate to sort column ascending"
                                                            style="width: 96px; text-align: left">BORROWED ON
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="QUANTITY: activate to sort column ascending"
                                                            style="width: 96px; text-align: left">BORROWER
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="STATUS: activate to sort column ascending"
                                                            style="width: 67px; text-align: center">STATUS
                                                        </th>
                                                        <th class="sorting" tabindex="0" aria-controls="order-listing"
                                                            rowspan="1" colspan="1"
                                                            aria-label="Actions: activate to sort column ascending"
                                                            style="width: 64px; text-align: center">ACTIONS
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:set var="orderList" value="${requestScope.ORDER_LIST}"/>

                                                    <c:forEach var="order" items="${orderList}"
                                                               varStatus="counter">
                                                        <tr class="odd">
                                                            <form action="DispatchServlet">
                                                                <!--Start: Order Item Form-->
                                                                <td class="sorting_1"
                                                                    style="text-align: center">${counter.count}</td>
                                                                <td style="text-align: left">
                                                                        ${order.orderDate}
                                                                </td>
                                                                <td style="text-align: left">${order.memberID}</td>
                                                                <td style="text-align: center">
                                                                    <c:choose>
                                                                        <c:when test="${order.activeStatus eq -1}">
                                                                            <label class="badge badge-secondary">Cancelled</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 0}">
                                                                            <label class="badge badge-warning">Pending</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 1}">
                                                                            <label class="badge badge-info">Approved</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 2}">
                                                                            <label class="badge badge-primary">Delivered</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 3}">
                                                                            <label class="badge badge-success">Closed</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 4}">
                                                                            <label class="badge badge-danger">Overdue</label>
                                                                        </c:when>
                                                                        <c:when test="${order.activeStatus eq 5}">
                                                                            <label class="badge badge-dark">Rejected</label>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </td>
                                                            </form>
                                                            <!--End: order Item Form-->
                                                            <!--Start: Delete order Form-->
                                                            <form action="DispatchServlet">
                                                                <input type="hidden" value="${order.id}"
                                                                       name="pk">
                                                                <input type="hidden" value="${order.id}"
                                                                       name="orderPk">
                                                                <input type="hidden" name="txtSearchValue"
                                                                       value="${param.txtSearchValue}"/>
                                                                <td style="text-align: center">
                                                                    <div class="btn-group">
                                                                        <button type="submit" class="btn btn-light"
                                                                                name="btAction" value="View Details">
                                                                            <i class="fa fa-eye text-primary"></i>
                                                                        </button>
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#updateModal${order.id}"
                                                                                title="Update"
                                                                                data-original-title="Edit">
                                                                            <i class="fa fa-pencil text-primary"></i>
                                                                        </button>

                                                                        <button type="submit" class="btn btn-light"
                                                                                name="btAction" value="Delete order">
                                                                            <i class="fa fa-times text-primary"></i>
                                                                        </button>
                                                                    </div>
                                                                </td>
                                                            </form>
                                                            <!--End: Delete order Form-->
                                                            <!--Start: Update order Form-->
                                                            <form action="DispatchServlet" enctype="multipart/form-data"
                                                                  method="POST">
                                                                <input type="hidden" value="${order.id}"
                                                                       name="pk">
                                                                <input type="hidden" name="txtSearchValue"
                                                                       value="${param.txtSearchValue}"/>
                                                                <div class="modal fade" id="updateModal${order.id}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="exampleModalLongTitle">
                                                                                    Edit order Details
                                                                                </h5>
                                                                                <button type="button"
                                                                                        class="close"
                                                                                        data-dismiss="modal"
                                                                                        aria-label="Close">
                                                                                    <span aria-hidden="true">&times;</span>
                                                                                </button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <div class="form-group row">
                                                                                    <div class="col-sm-9">
                                                                                        To-do
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="submit"
                                                                                        name="btAction"
                                                                                        value="Update order"
                                                                                        class="btn btn-primary"
                                                                                >
                                                                                    Save
                                                                                </button>
                                                                                <button type="button"
                                                                                        class="btn btn-outline-primary"
                                                                                        data-dismiss="modal">
                                                                                    Close
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                            <!--End: Update order Form-->
                                                        </tr>
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