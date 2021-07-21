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
    <meta charset="utf-8">
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
    <link rel="stylesheet" href="https://cdn.datatables.net/searchpanes/1.3.0/css/searchPanes.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/select/1.3.3/css/select.dataTables.min.css">
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <script src="js/iconpro.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<div class="container-scroller">
    <%-- partial:../../partials/_navbar.html --%>
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
    <%-- partial --%>
    <div class="container-fluid page-body-wrapper">
        <%-- partial --%>
        <%-- partial:../../partials/_sidebar.html --%>
        <jsp:include page="sidebar.jsp"></jsp:include>
        <%-- partial --%>
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Renewal</h4>
                        <div class="row">
                            <div class="table-responsive">
                                <div id="order-listing_wrapper" class="dataTables_wrapper dt-bootstrap4 no-footer">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <table id="rental-datatable" class="table dataTable no-footer" role="grid"
                                                   aria-describedby="order-listing_info">
                                                <thead>
                                                <tr>
                                                    <th style="width: 0px; text-align: center">#</th>
                                                    <th style="width: 0px; text-align: left">BOOK</th>
                                                    <th style="width: 0px; text-align: left">BORROWER</th>
                                                    <th style="width: 0px; text-align: center">STATUS</th>
                                                    <th style="width: 0px; text-align: center">ACTIONS</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:set var="renewalList" value="${requestScope.RENEWAL_LIST}"/>
                                                <c:forEach var="renewal" items="${renewalList}"
                                                           varStatus="counter">
                                                    <tr>
                                                        <td class="text-center">${counter.count}</td>
                                                        <td class="text-left">
                                                                ${renewal.item.title}
                                                        </td>
                                                        <td class="text-left">${renewal.item.memberName}</td>
                                                        <td class="text-center">
                                                            <label class="badge lbRenewalStat"
                                                                   id="lbRenewalStat${renewal.renewalID}"
                                                                   renewalid="${renewal.renewalID}"
                                                                   renewalStatus="${renewal.approvalStatus}"
                                                                   value="${renewal.approvalStatus}"></label>
                                                        </td>
                                                        <td class="text-center">
                                                            <div class="btn-group">
                                                                <button type="button" class="btn btn-light"
                                                                        data-toggle="modal"
                                                                        data-target="#renewalModal${renewal.renewalID}">
                                                                    <i class="fa fa-eye text-primary"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:forEach var="renewal" items="${renewalList}">
                                                <div class="modal fade"
                                                     id="renewalModal${renewal.renewalID}"
                                                     tabindex="-1">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">
                                                                    Renewal Request Details
                                                                </h5>
                                                                <button type="button"
                                                                        class="close"
                                                                        data-dismiss="modal">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="form-group row">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Request ID
                                                                    </label>
                                                                    <div class="col-lg-11 col-12">
                                                                        <input type="text"
                                                                               id="txtOrderID${renewal.renewalID}"
                                                                               class="form-control"
                                                                               value="${renewal.renewalID}"
                                                                               disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group row">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Member ID
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               id="txtMemberID${renewal.item.memberID}"
                                                                               class="form-control"
                                                                               value="${renewal.item.memberID}"
                                                                               disabled/>
                                                                    </div>
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Member Name
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               id="txtMemberName${renewal.item.memberID}"
                                                                               class="form-control"
                                                                               value="${renewal.item.memberName}"
                                                                               disabled/>
                                                                    </div>
                                                                </div>
                                                                <c:set var="staffID" value="${renewal.librarian.id}"/>
                                                                <c:set var="staffName" value="${renewal.librarian.name}"/>
                                                                <c:if test="${empty staffID}">
                                                                    <c:set var="staffID" value="N/A"/>
                                                                    <c:set var="staffName" value="N/A"/>
                                                                </c:if>
                                                                <div class="form-group row">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Staff ID
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               id="txtStaffID${staffID}"
                                                                               class="form-control frmStaffID"
                                                                               renewalid="${renewal.renewalID}"
                                                                               value="${staffID}"
                                                                               disabled/>
                                                                    </div>
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Staff Name
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               id="txtStaffName${staffID}"
                                                                               class="form-control frmStaffName"
                                                                               renewalid="${renewal.renewalID}"
                                                                               value="${staffName}"
                                                                               disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group row">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Book
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               id="txtBookID${renewal.item.title}"
                                                                               class="form-control"
                                                                               value="${renewal.item.title}"
                                                                               disabled/>
                                                                    </div>
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Extend Date
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               class="form-control"
                                                                               value="${renewal.requestedExtendDate}"
                                                                               disabled/>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group row">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Reason
                                                                    </label>
                                                                    <div class="col-lg-11 col-12">
                                                                        <textarea type="text" class="form-control"
                                                                               disabled>${renewal.reason}</textarea>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group row frmRenewalStat"
                                                                     renewalid="${renewal.renewalID}">
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Renewal Status
                                                                    </label>
                                                                    <div class="col-lg-5 col-12">
                                                                        <input type="text"
                                                                               class="form-control inpRenewalStat"
                                                                               id="inpRenewalStat${renewal.renewalID}"
                                                                               renewalid="${renewal.renewalID}"
                                                                               renewalStatus="${renewal.approvalStatus}"
                                                                               value="${renewal.approvalStatus}"
                                                                               disabled/>
                                                                    </div>
                                                                    <label class="col-lg-1 col-12 col-form-label">
                                                                        Approval Status
                                                                    </label>
                                                                    <c:choose>
                                                                        <c:when test="${renewal.approvalStatus eq 0}">
                                                                            <div class="col-lg-3 col-6 pr-0 contModalApprove"
                                                                                 renewalid="${renewal.renewalID}">
                                                                                <button type="button"
                                                                                        class="btn btn-block btn-light btn-sm rounded-0"
                                                                                        data-toggle="modal"
                                                                                        data-target="#mdConfirmApprove${renewal.renewalID}"
                                                                                        style="border-top-left-radius: 1rem !important;
                                                                                    border-bottom-left-radius: 1rem !important">
                                                                                    <h3 class="fa fa-check-circle text-success"></h3>
                                                                                </button>
                                                                            </div>
                                                                            <div class="col-lg-2 col-6 pl-0 contModalReject"
                                                                                 renewalid="${renewal.renewalID}">
                                                                                <button type="button"
                                                                                        class="btn btn-block btn-light btn-sm rounded-0"
                                                                                        data-toggle="modal"
                                                                                        data-target="#mdConfirmReject${renewal.renewalID}"
                                                                                        style="border-top-right-radius: 1rem !important;
                                                                                    border-bottom-right-radius: 1rem !important">
                                                                                    <h3 class="fa fa-times-circle text-danger"></h3>
                                                                                </button>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:when test="${(renewal.approvalStatus eq -1)
                                                                            or (renewal.approvalStatus eq 2)}">
                                                                            <div class="col-lg-5 col-12">
                                                                                <div class="btn btn-block btn-outline-danger btn-sm bg-white"
                                                                                     disabled>
                                                                                    <h3 class="fa fa-times-circle text-danger"></h3>
                                                                                </div>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <div class="col-lg-5 col-12">
                                                                                <div class="btn btn-block btn-outline-success btn-sm bg-white"
                                                                                     disabled>
                                                                                    <h3 class="fa fa-check-circle text-success"></h3>
                                                                                </div>
                                                                            </div>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
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
                                                <div class="modal fade"
                                                     id="mdConfirmApprove${renewal.renewalID}"
                                                     tabindex="-1"
                                                     style="overflow: hidden !important; ">
                                                    <div class="modal-dialog modal-dialog-centered"
                                                         style="margin-top: 0px !important;">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">
                                                                    Approve this Request
                                                                </h5>
                                                                <button type="button"
                                                                        class="close"
                                                                        data-dismiss="modal">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                This action cannot be undone. Are you sure?
                                                            </div>
                                                            <div class="modal-footer">
                                                                <div class="row">
                                                                    <div class="col-12">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary float-right ml-3"
                                                                                id="btnDismissAppr${renewal.renewalID}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnApproveRenewal${renewal.renewalID}"
                                                                                renewalid="${renewal.renewalID}"
                                                                                role="approveRenewal">
                                                                            Yes
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal fade"
                                                     id="mdConfirmReject${renewal.renewalID}"
                                                     tabindex="-1"
                                                     style="overflow: hidden !important; ">
                                                    <div class="modal-dialog modal-dialog-centered"
                                                         style="margin-top: 0px !important;">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">
                                                                    Reject this Order
                                                                </h5>
                                                                <button type="button"
                                                                        class="close"
                                                                        data-dismiss="modal">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                This action cannot be undone. Are you sure?
                                                            </div>
                                                            <div class="modal-footer">
                                                                <div class="row">
                                                                    <div class="col-12">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary float-right ml-3"
                                                                                id="btnDismissReject${renewal.renewalID}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnRejectRenewal${renewal.renewalID}"
                                                                                renewalid="${renewal.renewalID}"
                                                                                role="rejectRenewal">
                                                                            Yes
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%-- content-wrapper ends --%>
            <%-- partial:../../partials/_footer.html --%>
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2021.
                            Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin
                                template</a> from BootstrapDash. All rights reserved.</span>
                    <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made
                            with <i class="ti-heart text-danger ml-1"></i></span>
                </div>
            </footer>
            <%-- partial --%>
        </div>
        <%-- main-panel ends --%>
    </div>
    <%-- page-body-wrapper ends --%>
</div>
<%-- container-scroller --%>
<%-- plugins:js --%>
<script src="vendors/js/vendor.bundle.base.js"></script>
<%-- endinject --%>
<%-- Plugin js for this page --%>
<script src="vendors/datatables.net/jquery.dataTables.js"></script>
<script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
<script src="https://cdn.datatables.net/searchpanes/1.3.0/js/dataTables.searchPanes.min.js"></script>
<script src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>
<%-- End plugin js for this page --%>
<%-- inject:js --%>
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<script src="js/renewalmanagement.js"></script>
<%-- endinject --%>
<%-- Custom js for this page--%>

<%-- End custom js for this page--%>
</body>
</html>
