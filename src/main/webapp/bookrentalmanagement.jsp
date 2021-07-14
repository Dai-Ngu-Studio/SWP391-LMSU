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
                        <h4 class="card-title">Book Rental</h4>
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
                                                    <th style="width: 96px; text-align: left">ORDERED ON</th>
                                                    <th style="width: 96px; text-align: left">BORROWER</th>
                                                    <th style="width: 96px; text-align: left">SCHEDULED TIME</th>
                                                    <th style="width: 67px; text-align: center">STATUS</th>
                                                    <th style="width: 64px; text-align: center">ACTIONS</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <%--Map<Pair<OrderObj, DirectOrderObj>, List<OrderItemObj>>--%>
                                                <c:set var="orderList" value="${requestScope.ORDER_LIST}"/>
                                                <c:forEach var="order" items="${orderList}"
                                                           varStatus="counter">
                                                    <tr>
                                                        <td class="text-center">${counter.count}</td>
                                                        <td class="text-left">
                                                                ${order.key.key.orderDate}
                                                        </td>
                                                        <td class="text-left">${order.key.key.memberName}</td>
                                                        <td class="text-left">
                                                            <c:if test="${not empty order.key.value.scheduledTime}">
                                                                ${order.key.value.scheduledTime}
                                                            </c:if>
                                                            <c:if test="${empty order.key.value.scheduledTime}">
                                                                None
                                                            </c:if>
                                                        </td>
                                                        <td class="text-center lbOrderStat"
                                                            id="lbOrderStat${order.key.key.id}"
                                                            orderid="${order.key.key.id}">
                                                            <label class="badge"
                                                                   activeStatus="${order.key.key.activeStatus}"></label>
                                                        </td>
                                                        <td class="text-center">
                                                            <form action="DispatchServlet">
                                                                <div class="btn-group">
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#orderModal${order.key.key.id}">
                                                                        <i class="fa fa-eye text-primary"></i>
                                                                    </button>
                                                                        <%--not implemented--%>
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#updateModal${order.key.key.id}"
                                                                            title="Update"
                                                                            data-original-title="Edit">
                                                                        <i class="fa fa-pencil text-primary"></i>
                                                                    </button>
                                                                </div>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:forEach var="order" items="${orderList}">
                                                <%--Start: Order Details Form--%>
                                                <form action="DispatchServlet">
                                                    <div class="modal fade"
                                                         id="orderModal${order.key.key.id}"
                                                         tabindex="-1">
                                                        <div class="modal-dialog modal-lg">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title">
                                                                        Order Details
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
                                                                            Order ID
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   id="txtOrderID${order.key.key.id}"
                                                                                   class="form-control"
                                                                                   value="${order.key.key.id}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Order Date
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${order.key.key.orderDate}"
                                                                                   disabled/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Member ID
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${order.key.key.memberID}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Member Name
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${order.key.key.memberName}"
                                                                                   disabled/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Librarian ID
                                                                        </label>
                                                                        <c:set var="librarianID"
                                                                               value="${order.key.value.librarianID}"/>
                                                                        <c:set var="librarianName"
                                                                               value="${order.key.value.librarianName}"/>
                                                                        <c:if test="${empty librarianID}">
                                                                            <c:set var="librarianID" value="N/A"/>
                                                                            <c:set var="librarianName" value="N/A"/>
                                                                        </c:if>
                                                                        <div class="col-lg-5 col-12 frmLibrarianID"
                                                                             orderid="${order.key.key.id}">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${librarianID}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Librarian Name
                                                                        </label>
                                                                        <div class="col-lg-5 col-12 frmLibrarianName"
                                                                             orderid="${order.key.key.id}">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${librarianName}"
                                                                                   disabled/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row frmOrderStat"
                                                                         orderid="${order.key.key.id}">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Order Status
                                                                        </label>
                                                                        <div class="col-lg-5 col-12 lbOrderStat"
                                                                             id="pOrderStat${order.key.key.id}"
                                                                             orderid="${order.key.key.id}">
                                                                            <p class="form-control"
                                                                               activeStatus="${order.key.key.activeStatus}"
                                                                               orderid="${order.key.key.id}">
                                                                            </p>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Approval Status
                                                                        </label>
                                                                        <c:choose>
                                                                            <c:when test="${order.key.key.activeStatus eq 0}">
                                                                                <div class="col-3 pr-0 contModalApprove"
                                                                                     orderid="${order.key.key.id}">
                                                                                    <button type="button"
                                                                                            class="btn btn-block btn-light btn-sm rounded-0"
                                                                                            data-toggle="modal"
                                                                                            data-target="#mdConfirmApprove${order.key.key.id}"
                                                                                            style="border-top-left-radius: 1rem !important;
                                                                                    border-bottom-left-radius: 1rem !important">
                                                                                        <h3 class="fa fa-check-circle text-success"></h3>
                                                                                    </button>
                                                                                </div>

                                                                                <div class="col-2 pl-0 contModalReject"
                                                                                     orderid="${order.key.key.id}">
                                                                                    <button type="button"
                                                                                            class="btn btn-block btn-light btn-sm rounded-0"
                                                                                            data-toggle="modal"
                                                                                            data-target="#mdConfirmReject${order.key.key.id}"
                                                                                            style="border-top-right-radius: 1rem !important;
                                                                                    border-bottom-right-radius: 1rem !important">
                                                                                        <h3 class="fa fa-times-circle text-danger"></h3>
                                                                                    </button>
                                                                                </div>
                                                                            </c:when>
                                                                            <c:when test="${(order.key.key.activeStatus eq -1)
                                                                            or (order.key.key.activeStatus eq 8)}">
                                                                                <div class="col-lg-5 col-12">
                                                                                    <div class="btn btn-block btn-outline-danger btn-sm bg-white"
                                                                                         disabled>
                                                                                        <h3 class="fa fa-check-circle text-danger"></h3>
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
                                                                        <%--Documenting finished work:--%>
                                                                        <%--Update status for order-%>
                                                                        <%--update status for order item-%>
                                                                        <%--AJAX checks if order has been cancelled, rejected or failed to be approved-%>
                                                                        <%--Received uses SQL current timestamp--%>
                                                                        <%--Update the dropdown update status after approving--%>
                                                                        <%--Add Librarian ID, Name of Librarian who changed to received--%>
                                                                        <%--Only allow update items when order status is not pending, cancelled or reserve only--%>
                                                                        <%--Order Details Edit Button--%>
                                                                        <%--Items that can be updated: ITEM_APPROVED, ITEM_RECEIVED, ITEM_OVERDUE, ITEM_LOST--%>
                                                                        <%--Item Status direction:
                                                                        PENDING -> APPROVED (using approve button),
                                                                        APPROVED -> RECEIVED (using update button),
                                                                        OVERDUE is determined by system (system check is to be implemented),
                                                                        LOST is determined by system (~),
                                                                        OVERDUE -> OVERDUE_RETURNED (opposite direction is a lot of coding overhead),
                                                                        LOST -> OVERDUE_RETURNED (~)
                                                                        --%>
                                                                        <%--Overdue can only be updated to overdue returned--%>
                                                                        <%--AJAX check if all items status
                                                                        then update order status accordingly--%>
                                                                        <%--ITEMs that are scheduled for return are not concerned by Librarian, not allow updating,
                                                                        system will update when Manager received--%>
                                                                        <%--Notify when cancelled, rejected--%>
                                                                        <%--Librarian can't update item with status scheduled return,
                                                                        only manager can update--%>
                                                                        <%--Reserve only order can't be approved or updated--%>
                                                                        <%--Some to-do: --%>
                                                                        <%--Add Status Lock button to prevent accidental update--%>
                                                                        <%--CANCELLED is either by member or system do 1 day after scheduled date--%>
                                                                    <div class="row">
                                                                        <div class="col-12 text-center">
                                                                            <div class="btn-group groupBtnEdit"
                                                                                 orderID="${order.key.key.id}">
                                                                                <button type="button"
                                                                                        class="btn btn-light"
                                                                                        orderID="${order.key.key.id}">
                                                                                    <h4 class="fa fa-pencil text-primary"></h4>
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                        <%--Place to swap buttons in and out of view--%>
                                                                    <div class="row storageBtn"
                                                                         id="hiddenBtnStorage${order.key.key.id}"
                                                                         orderID="${order.key.key.id}"
                                                                         hidden>
                                                                        <button type="button" class="btn btn-light"
                                                                                orderID="${order.key.key.id}"
                                                                                role="confirmEdit">
                                                                            <h4 class="fa fa-check text-success"></h4>
                                                                        </button>
                                                                        <button type="button" class="btn btn-light"
                                                                                orderID="${order.key.key.id}"
                                                                                role="cancelEdit">
                                                                            <h4 class="fa fa-times text-danger"></h4>
                                                                        </button>
                                                                    </div>
                                                                    <div class="row">
                                                                        <table class="table table-hover table-responsive w-100 d-block d-xl-table">
                                                                            <thead>
                                                                            <tr>
                                                                                <th class="text-right">Item ID</th>
                                                                                <th class="text-left">Book Title
                                                                                </th>
                                                                                <th class="text-center">Status</th>
                                                                                <th class="text-left">Deadline</th>
                                                                                <th class="text-left">Received on
                                                                                </th>
                                                                                <th class="text-left">Returned on
                                                                                </th>
                                                                            </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                            <c:forEach var="orderItem"
                                                                                       items="${order.value}">
                                                                                <tr>
                                                                                    <td class="text-right">${orderItem.id} </td>
                                                                                    <td class="text-left">${orderItem.title}</td>
                                                                                    <td class="text-center">
                                                                                        <c:set var="statOrderItem"
                                                                                               value="${orderItem.lendStatus}"
                                                                                        />
                                                                                        <div class="contSlItemStat"
                                                                                             orderid="${order.key.key.id}"
                                                                                             orderitemid="${orderItem.id}"
                                                                                             style="display: none">
                                                                                            <select class="custom-select slItemStat"
                                                                                                    id="slItemStat${orderItem.id}"
                                                                                                    orderid="${order.key.key.id}"
                                                                                                    orderitemid="${orderItem.id}">
                                                                                            </select>
                                                                                        </div>
                                                                                        <div class="contItemStat"
                                                                                             orderid="${order.key.key.id}"
                                                                                             orderitemid="${orderItem.id}">
                                                                                            <label class="badge lbItemStat"
                                                                                                   id="lbItemStat${orderItem.id}"
                                                                                                   orderid="${order.key.key.id}"
                                                                                                   orderitemid="${orderItem.id}"
                                                                                                   lendStatus="${statOrderItem}">
                                                                                            </label>
                                                                                        </div>
                                                                                    </td>
                                                                                    <td class="text-left"
                                                                                        id="dateDeadline${orderItem.id}"
                                                                                        datevalue="${orderItem.returnDeadline}">
                                                                                        <c:if test="${empty orderItem.returnDeadline}">
                                                                                            N/A
                                                                                        </c:if>
                                                                                        <c:if test="${not empty orderItem.returnDeadline}">
                                                                                            ${orderItem.returnDeadline}
                                                                                        </c:if>
                                                                                    </td>
                                                                                    <td class="text-left"
                                                                                        id="dateLend${orderItem.id}"
                                                                                        datevalue="${orderItem.lendDate}">
                                                                                        <c:if test="${empty orderItem.lendDate}">
                                                                                            N/A
                                                                                        </c:if>
                                                                                        <c:if test="${not empty orderItem.lendDate}">
                                                                                            ${orderItem.lendDate}
                                                                                        </c:if>
                                                                                    </td>
                                                                                    <td class="text-left"
                                                                                        id="dateReturn${orderItem.id}"
                                                                                        datevalue="${orderItem.returnDate}">
                                                                                        <c:if test="${empty orderItem.returnDate}">
                                                                                            N/A
                                                                                        </c:if>
                                                                                        <c:if test="${not empty orderItem.returnDate}">
                                                                                            ${orderItem.returnDate}
                                                                                        </c:if>
                                                                                    </td>
                                                                                </tr>
                                                                            </c:forEach>
                                                                            </tbody>
                                                                        </table>
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
                                                </form>
                                                <%--End: Order Details Form--%>
                                                <div class="modal fade"
                                                     id="mdConfirmApprove${order.key.key.id}"
                                                     tabindex="-1"
                                                     style="overflow: hidden !important; ">
                                                    <div class="modal-dialog modal-dialog-centered"
                                                         style="margin-top: 0px !important;">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title">
                                                                    Approve this Order
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
                                                                                id="btnDismissAppr${order.key.key.id}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnApproveOrder${order.key.key.id}"
                                                                                orderid="${order.key.key.id}"
                                                                                role="approveOrder">
                                                                            Yes
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal fade"
                                                     id="mdConfirmReject${order.key.key.id}"
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
                                                                                id="btnDismissReject${order.key.key.id}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnRejectOrder${order.key.key.id}"
                                                                                orderid="${order.key.key.id}"
                                                                                role="rejectOrder">
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
<%-- End plugin js for this page --%>
<%-- inject:js --%>
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<script src="js/bookrentalmanagement.js"></script>
<%-- endinject --%>
<%-- Custom js for this page--%>

<%-- End custom js for this page--%>
</body>
</html>
