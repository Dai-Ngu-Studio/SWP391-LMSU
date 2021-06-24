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
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <%-- Fontawsome CDN --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
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
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial -->
        <!-- partial:../../partials/_sidebar.jsp -->
        <jsp:include page="sidebar.jsp"/>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">User Management</h4>
                        <div class="row">
                            <div class="col-12">
                                <div class="table-responsive">
                                    <div class="dataTables_wrapper dt-bootstrap4 no-footer">
                                        <div class="row">
                                            <div class="col-sm-12 col-md-6">
                                                <div id="order-listing_filter" class="dataTables_filter">
                                                    <c:if test="${requestScope.ADD_DUPLICATE}">
                                                        <div class="alert alert-danger">
                                                                ${requestScope.ADD_DUPLICATE}
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="table-responsive">
                                                <table id="user-datatable"
                                                       class="table table-hover dataTable no-footer my-2">
                                                    <thead>
                                                    <tr role="row">
                                                        <th class="text-right" style="width: 0px;">#
                                                        </th>
                                                        <th style="width: 96px;">MEMBER ID
                                                        </th>
                                                        <th style="width: 56px;">NAME
                                                        </th>
                                                        <th style="width: 67px;">EMAIL
                                                        </th>
                                                        <th class="text-center" style="width: 56px;">STATUS
                                                        </th>
                                                        <th class="text-center" style="width: 64px;">ACTIONS
                                                        </th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:set var="memberList" value="${requestScope.MEMBER_LIST}"/>
                                                    <c:forEach var="member" items="${memberList}" varStatus="counter">
                                                        <tr>

                                                            <td class="text-right">${counter.count}</td>
                                                            <td class="text-left">${member.id}</td>
                                                            <td class="text-left">${member.name}</td>
                                                            <td class="text-left">${member.email}</td>
                                                            <td class="text-center">
                                                                <c:if test="${member.activeStatus eq 'false'}">
                                                                    <span class="badge badge-warning text-center">Inactive</span>
                                                                </c:if>
                                                                <c:if test="${member.activeStatus eq 'true'}">
                                                                    <span class="badge badge-success text-center">Active</span>
                                                                </c:if>
                                                            </td>
                                                            <td class="text-center">
                                                                <form action="DispatchServlet"
                                                                      enctype="multipart/form-data"
                                                                      method="POST">
                                                                    <input type="hidden" name="userPk"
                                                                           value="${member.id}">
                                                                    <input type="hidden" name="txtSearchValue"
                                                                           value="${param.txtSearchValue}"/>
                                                                        <%-- Group button --%>
                                                                    <div class="btn-group">
                                                                            <%--Button and view modal--%>
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#viewModal${member.id}"
                                                                                title="View user information">
                                                                            <i class="fa fa-eye text-primary"></i>
                                                                        </button>
                                                                        <div class="modal fade"
                                                                             id="viewModal${member.id}"
                                                                             tabindex="-1"
                                                                             role="dialog"
                                                                             aria-labelledby="exampleModalLongTitle"
                                                                             aria-hidden="true">
                                                                            <div class="modal-dialog"
                                                                                 style="margin-top: 30px"
                                                                                 role="document">
                                                                                <div class="modal-content">
                                                                                    <div class="modal-header">
                                                                                        <h5 class="modal-title"
                                                                                            id="exampleModalLongTitle">
                                                                                            View User Details
                                                                                        </h5>
                                                                                        <button type="button"
                                                                                                class="close"
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
                                                                                                         style="height: 250px; width: auto;"
                                                                                                         src="${pageContext.request.contextPath}/image/${member.profilePicturePath}"
                                                                                                         id="coverPictureView${member.id}"
                                                                                                         alt="Avatar"
                                                                                                         onerror="this.onerror=null; this.src='images/default-user-icon.png';"
                                                                                                    />
                                                                                                    <input type="hidden"
                                                                                                           name="txtCoverFile"
                                                                                                           value="${member.profilePicturePath}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group row">
                                                                                                <label class="col-sm-3 col-form-label">Name
                                                                                                </label>
                                                                                                <div class="col-sm-9">
                                                                                                    <input type="text"
                                                                                                           readonly
                                                                                                           class="form-control"
                                                                                                           value="${member.name}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group row">
                                                                                                <label class="col-sm-3 col-form-label">Email</label>
                                                                                                <div class="col-sm-9">
                                                                                                    <input type="text"
                                                                                                           readonly
                                                                                                           class="form-control"
                                                                                                           value="${member.email}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group row">
                                                                                                <label class="col-sm-3 col-form-label">Phone
                                                                                                    Number</label>
                                                                                                <div class="col-sm-9">
                                                                                                    <input type="text"
                                                                                                           readonly
                                                                                                           class="form-control"
                                                                                                           value="${member.phoneNumber}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group row">
                                                                                                <label class="col-sm-3 col-form-label">Semester
                                                                                                </label>
                                                                                                <div class="col-sm-9">
                                                                                                    <input type="text"
                                                                                                           readonly
                                                                                                           class="form-control"
                                                                                                           value="${member.semester}">
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="form-group row">
                                                                                                <label class="col-sm-3 col-form-label">
                                                                                                    Active status
                                                                                                </label>
                                                                                                <div class="col-sm-9">
                                                                                                    <c:if test="${member.activeStatus eq 'false'}">
                                                                                                        <input type="text"
                                                                                                               readonly
                                                                                                               class="form-control"
                                                                                                               value="Inactive">
                                                                                                    </c:if>
                                                                                                    <c:if test="${member.activeStatus eq 'true'}">
                                                                                                        <input type="text"
                                                                                                               readonly
                                                                                                               class="form-control"
                                                                                                               value="Active">
                                                                                                    </c:if>
                                                                                                </div>
                                                                                            </div>
                                                                                        </form>
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="button"
                                                                                                class="btn btn-outline-primary"
                                                                                                data-dismiss="modal">
                                                                                            Close
                                                                                        </button>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                            <%--End button and view modal--%>

                                                                            <%--Button and update modal--%>
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#updateModal${member.id}"
                                                                                title="Update"
                                                                                data-original-title="Edit">
                                                                            <i class="fa fa-pencil text-primary"></i>
                                                                        </button>
                                                                        <div class="modal fade"
                                                                             id="updateModal${member.id}"
                                                                             tabindex="-1"
                                                                             role="dialog"
                                                                             aria-labelledby="ariaUpdateModal${member.id}"
                                                                             aria-hidden="true">
                                                                            <div class="modal-dialog"
                                                                                 style="margin-top: 30px"
                                                                                 role="document">
                                                                                <div class="modal-content">
                                                                                    <div class="modal-header">
                                                                                        <h5 class="modal-title">
                                                                                            Edit User Details
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
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                User avatar
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <img class="rounded float-right"
                                                                                                     style="height: 250px; width: auto;"
                                                                                                     src="${pageContext.request.contextPath}/image/${member.profilePicturePath}"
                                                                                                     id="coverPictureUpdate${member.id}"
                                                                                                     alt="User avatar"
                                                                                                     onerror="this.onerror=null; this.src='images/default-user-icon.png';"
                                                                                                />
                                                                                                <input type="hidden"
                                                                                                       name="txtCoverFile"
                                                                                                       value="${member.profilePicturePath}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <div class="col-sm-3">
                                                                                            </div>
                                                                                            <div class="col-sm-9">
                                                                                                <div class="custom-file">
                                                                                                    <input type="file"
                                                                                                           class="custom-file-input"
                                                                                                           id="customFileUpdate${member.id}"
                                                                                                           name="coverPicture"
                                                                                                           onchange="readURL(this, 'coverPictureUpdate${member.profilePicturePath}');"
                                                                                                    >
                                                                                                    <label class="custom-file-label"
                                                                                                           for="customFileUpdate${member.id}">
                                                                                                        Choose Image
                                                                                                    </label>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Member ID
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text"
                                                                                                       readonly
                                                                                                       class="form-control"
                                                                                                       value="${member.id}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Member name
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="text"
                                                                                                       class="form-control"
                                                                                                       name="txtUpdateMemberName"
                                                                                                       value="${member.name}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Semester No.
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="number"
                                                                                                       min="0"
                                                                                                       max="9"
                                                                                                       readonly
                                                                                                       class="form-control"
                                                                                                       value="${member.semester}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Email
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="email"
                                                                                                       required
                                                                                                       readonly
                                                                                                       class="form-control"
                                                                                                       value="${member.email}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Phone Number
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <input type="number"
                                                                                                       minlength="10"
                                                                                                       maxlength="10"
                                                                                                       required
                                                                                                       class="form-control"
                                                                                                       name="txtUpdatePhoneNumber"
                                                                                                       value="${member.phoneNumber}">
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="form-group row">
                                                                                            <label class="col-sm-3 col-form-label">
                                                                                                Active status
                                                                                            </label>
                                                                                            <div class="col-sm-9">
                                                                                                <c:if test="${member.activeStatus eq 'false'}">
                                                                                                    <input type="text"
                                                                                                           class="form-control"
                                                                                                           name="updateActiveStatus"
                                                                                                           value="Inactive">
                                                                                                </c:if>
                                                                                                <c:if test="${member.activeStatus eq 'true'}">
                                                                                                    <input type="text"
                                                                                                           class="form-control"
                                                                                                           name="updateActiveStatus"
                                                                                                           value="Active">
                                                                                                </c:if>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="submit"
                                                                                                name="btAction"
                                                                                                value="Update User"
                                                                                                class="btn btn-primary">
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
                                                                            <%--End button and update modal--%>

                                                                            <%--Button and Delete modal--%>
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal" title="Delete"
                                                                                data-target="#deleteModal${member.id}"
                                                                                data-original-title="Remove">
                                                                            <i class="fa fa-times text-primary"></i>
                                                                        </button>
                                                                        <div class="modal fade"
                                                                             id="deleteModal${member.id}"
                                                                             tabindex="-1"
                                                                             role="dialog"
                                                                             aria-labelledby="ariaDeleteModal${member.id}"
                                                                             aria-hidden="true">
                                                                            <div class="modal-dialog"
                                                                                 role="document">
                                                                                <div class="modal-content">
                                                                                    <div class="modal-header">
                                                                                        <h5 class="modal-title">
                                                                                            WARNING
                                                                                        </h5>
                                                                                        <button type="button"
                                                                                                class="close"
                                                                                                data-dismiss="modal"
                                                                                                aria-label="Close">
                                                                                            <span aria-hidden="true">&times;</span>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        Do you want to delete this
                                                                                        user?
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="submit"
                                                                                                name="btAction"
                                                                                                value="Delete User"
                                                                                                class="btn btn-primary">
                                                                                            Yes
                                                                                        </button>
                                                                                        <button type="button"
                                                                                                class="btn btn-outline-primary"
                                                                                                data-dismiss="modal">
                                                                                            Cancel
                                                                                        </button>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                            <%--End button and Delete modal--%>
                                                                    </div>
                                                                        <%-- End group button --%>
                                                                </form>
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
