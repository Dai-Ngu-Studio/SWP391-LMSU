<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Date: 6/2/2021
  Time: 8:29 PM
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
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <script src="js/authormanagement.js"></script>
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
        <!-- partial:../../partials/_sidebar.html -->
        <jsp:include page="sidebar.jsp"/>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Data table</h4>
                        <div class="table-responsive">
                            <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <table id="author-datatable" class="table dataTable no-footer my-2"
                                               role="grid"
                                               aria-describedby="order-listing_info">
                                            <thead>
                                            <tr>
                                                <th style="width: 0px; text-align: right">#</th>
                                                <th style="width: 96px; text-align: left">NAME</th>
                                                <%--                                                <th style="width: 67px; text-align: left"></th>--%>
                                                <th style="width: 64px; text-align: center">Actions</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="authorList" value="${requestScope.AUTHOR_LIST}"/>
                                            <c:forEach var="author" items="${authorList}"
                                                       varStatus="counter">
                                                <tr>
                                                    <form action="DispatchServlet"
                                                          enctype="multipart/form-data"
                                                          method="POST">
                                                        <!--Start: Author Item Form-->
                                                        <td class="sorting_1"
                                                            style="text-align: right">${counter.count}</td>
                                                        <td style="text-align: left">
                                                                ${author.authorName}
                                                        </td>
                                                        <c:set var="bookMap" value="${requestScope.COUNT_BOOK}"/>
                                                        <input type="hidden" value="${author.authorID}"
                                                               name="pk">
                                                        <input type="hidden" value="${author.authorID}"
                                                               name="authorPk">
                                                        <input type="hidden" name="txtSearchValue"
                                                               value="${param.txtSearchValue}"/>
                                                        <td style="text-align: right;">
                                                            <div class="btn-group">
                                                                <button type="submit" class="btn btn-light"
                                                                        name="btAction" value="View Authors"
                                                                        title="Details">
                                                                    <i class="fa fa-eye text-primary"></i>
                                                                </button>
                                                                <button type="button" class="btn btn-light"
                                                                        data-toggle="modal"
                                                                        data-target="#updateModal${author.authorID}"
                                                                        title="Update"
                                                                        data-original-title="Edit">
                                                                    <i class="fa fa-pencil text-primary"></i>
                                                                </button>
                                                                <button type="button" class="btn btn-light"
                                                                        data-toggle="modal"
                                                                        data-target="#deleteModal${author.authorID}"
                                                                        title="Delete"
                                                                        data-original-title="Remove">
                                                                    <i class="fa fa-times text-primary"></i>
                                                                </button>
                                                                <!--Start: Update Author Modal-->
                                                                <div class="modal fade"
                                                                     id="updateModal${author.authorID}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="ariaUpdateModal${author.authorID}"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="exampleModalLongTitle">
                                                                                    Edit Author Details
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
                                                                                    <label class="col-sm-3 col-form-label">Author
                                                                                        Cover
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <img class="rounded float-right"
                                                                                             style="height: 280px; width: auto;"
                                                                                             src="${pageContext.request.contextPath}/image/${author.coverPath}"
                                                                                             id="coverPictureUpdate${author.authorID}"
                                                                                             alt="Book cover"
                                                                                             onerror="this.onerror=null; this.src='images/imagenotfound.jpg';"
                                                                                        />
                                                                                        <input type="hidden"
                                                                                               name="txtCoverFile"
                                                                                               value="${author.coverPath}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <div class="col-sm-3">
                                                                                    </div>
                                                                                    <div class="col-sm-9">
                                                                                        <div class="custom-file">
                                                                                            <input type="file"
                                                                                                   class="custom-file-input"
                                                                                                   id="customFileUpdate${author.authorID}"
                                                                                                   name="coverPicture"
                                                                                                   onchange="readURL(this, 'coverPictureUpdate${author.authorID}');"
                                                                                            >
                                                                                            <label class="custom-file-label"
                                                                                                   for="customFileUpdate${author.authorID}">Choose
                                                                                                Image
                                                                                            </label>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">Name</label>
                                                                                    <div class="col-sm-9">
                                                                                        <input type="text"
                                                                                               class="form-control"
                                                                                               name="txtUpdateAuthorName"
                                                                                               value="${author.authorName}">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row">
                                                                                    <label class="col-sm-3 col-form-label">
                                                                                        Author Bio
                                                                                    </label>
                                                                                    <div class="col-sm-9">
                                                                                        <textarea
                                                                                                class="form-control"
                                                                                                name="txtUpdateAuthorBio"
                                                                                                id="${author.authorID}"
                                                                                                rows="10"> ${author.authorBio}
                                                                                        </textarea>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="submit"
                                                                                        name="btAction"
                                                                                        value="Update Author"
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
                                                                <!--End: Update Author Modal-->

                                                                <!--Start: Delete Author Modal-->
                                                                <div class="modal fade"
                                                                     id="deleteModal${author.authorID}"
                                                                     tabindex="-1"
                                                                     role="dialog"
                                                                     aria-labelledby="ariaDeleteModal${author.authorID}"
                                                                     aria-hidden="true">
                                                                    <div class="modal-dialog" role="document">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title"
                                                                                    id="exampleModalLongTitle2">
                                                                                    WARNING
                                                                                </h5>
                                                                                <button type="button"
                                                                                        class="close"
                                                                                        data-dismiss="modal"
                                                                                        aria-label="Close">
                                                                                    <span aria-hidden="true">&times;</span>
                                                                                </button>
                                                                            </div>
                                                                            <c:choose>
                                                                                <c:when test="${bookMap.get(author.authorID) > 0}">
                                                                                    <div class="modal-body">
                                                                                        You haven't deleted all the
                                                                                        books from
                                                                                        this author
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="button"
                                                                                                class="btn btn-outline-primary"
                                                                                                data-dismiss="modal">
                                                                                            Close
                                                                                        </button>
                                                                                    </div>
                                                                                </c:when>
                                                                                <c:when test="${bookMap.get(author.authorID) eq null}">
                                                                                    <div class="modal-body">
                                                                                        Do you want to delete this
                                                                                        author?
                                                                                    </div>
                                                                                    <div class="modal-footer">
                                                                                        <button type="submit"
                                                                                                name="btAction"
                                                                                                value="Delete Author"
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
                                                                                </c:when>
                                                                            </c:choose>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <!--End: Delete Author Modal-->
                                                            </div>
                                                        </td>
                                                    </form>
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
