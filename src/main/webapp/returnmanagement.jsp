<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <a class="navbar-brand brand-logo mr-5" href="dashboard.jsp">
                <img src="images/LMSU LOGO DASHBOARD.svg" class="mr-2" alt="logo" style="margin-left: 40px;"/>
            </a>
            <a class="navbar-brand brand-logo-mini" href="dashboard.jsp">
                <img src="images/LMSU LOGO MINI DASHBOARD.svg" alt="logo"/>
            </a>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
                <span class="icon-menu"></span>
            </button>

            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" id="profileDropdown">
                        <img src="${sessionScope.LOGIN_USER.profilePicturePath}" alt="profile picture"
                             style="border-radius: 50%;"
                             onerror="this.onerror=null; this.src='images/default-user-icon.png';"/>
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
                        <h4 class="card-title">Returns</h4>
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
                                                    <th style="width: 0px; text-align: center">METHOD</th>
                                                    <th style="width: 0px; text-align: left">ORDERED ON</th>
                                                    <th style="width: 0px; text-align: left">BORROWER</th>
                                                    <th style="width: 0px; text-align: center">STATUS</th>
                                                    <th style="width: 0px; text-align: center">ACTIONS</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <%--Map<Pair<DirectOrderObj, DeliveryOrderObj>, Pair<OrderObj, List<OrderItemObj>>>--%>
                                                <div id="userRole" userRole="" hidden></div>
                                                <c:set var="orderList" value="${requestScope.ORDER_LIST}"/>
                                                <c:forEach var="order" items="${orderList}"
                                                           varStatus="counter">
                                                    <c:set var="lendMethod" value="${order.value.key.lendMethod}"/>
                                                    <%--Return orders are not shown in this page--%>
                                                    <c:if test="${order.key.key.returnOrder}">
                                                        <tr>
                                                            <td class="text-center">${counter.count}</td>
                                                            <td class="text-center">
                                                                <c:if test="${lendMethod}">
                                                                    Delivery
                                                                </c:if>
                                                                <c:if test="${not lendMethod}">
                                                                    Direct
                                                                </c:if>
                                                            </td>
                                                            <td class="text-left">
                                                                    ${order.value.key.orderDate}
                                                            </td>
                                                            <td class="text-left">${order.value.key.memberName}
                                                                (${order.value.key.memberID})
                                                            </td>
                                                            <td class="text-center lbOrderStat"
                                                                id="lbOrderStat${order.value.key.id}"
                                                                orderid="${order.value.key.id}">
                                                                <label class="badge"
                                                                       activeStatus="${order.value.key.activeStatus}"
                                                                       value="${order.value.key.activeStatus}"></label>
                                                            </td>
                                                            <td class="text-center">
                                                                <form action="DispatchServlet">
                                                                    <div class="btn-group">
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#orderModal${order.value.key.id}">
                                                                            <i class="fa fa-eye text-primary"></i>
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:forEach var="order" items="${orderList}">
                                                <%--Start: Order Details Form--%>
                                                <form action="DispatchServlet">
                                                    <div class="modal fade"
                                                         id="orderModal${order.value.key.id}"
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
                                                                            Return ID
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   id="txtOrderID${order.value.key.id}"
                                                                                   class="form-control"
                                                                                   value="${order.value.key.id}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Created Date
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${order.value.key.orderDate}"
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
                                                                                   value="${order.value.key.memberID}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Member Name
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${order.value.key.memberName}"
                                                                                   disabled/>
                                                                        </div>
                                                                    </div>
                                                                    <c:if test="${not order.value.key.lendMethod}">
                                                                        <div class="form-group row">
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Scheduled Time
                                                                            </label>
                                                                            <div class="col-lg-11 col-12"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.key.scheduledTime}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                    <c:if test="${order.value.key.lendMethod}">
                                                                        <div class="form-group row">
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Receiver Name
                                                                            </label>
                                                                            <div class="col-lg-5 col-12"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.receiverName}"
                                                                                       disabled/>
                                                                            </div>
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Phone Number
                                                                            </label>
                                                                            <div class="col-lg-5 col-12"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.phoneNumber}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Street Address
                                                                            </label>
                                                                            <div class="col-lg-11 col-12"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.deliveryAddress1}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <c:if test="${(not empty fn:trim(order.key.value.deliveryAddress2))}">
                                                                            <div class="form-group row">
                                                                                <label class="col-lg-1 col-12 col-form-label">
                                                                                    Residence Address
                                                                                </label>
                                                                                <div class="col-lg-11 col-12"
                                                                                     orderid="${order.value.key.id}">
                                                                                    <input type="text"
                                                                                           class="form-control"
                                                                                           value="${order.key.value.deliveryAddress2}"
                                                                                           disabled/>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>
                                                                        <div class="form-group row">
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                City
                                                                            </label>
                                                                            <div class="col-lg-11 col-12 txtCity"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.city}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                District
                                                                            </label>
                                                                            <div class="col-lg-5 col-12 txtDistrict"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.district}"
                                                                                       disabled/>
                                                                            </div>
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Ward
                                                                            </label>
                                                                            <div class="col-lg-5 col-12 txtWard"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.ward}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                    <div class="form-group row frmOrderStat"
                                                                         orderid="${order.value.key.id}">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Order Status
                                                                        </label>
                                                                        <div class="col-lg-11 col-12 lbOrderStat"
                                                                             id="pOrderStat${order.value.key.id}"
                                                                             orderid="${order.value.key.id}">
                                                                            <p class="form-control"
                                                                               activeStatus="${order.value.key.activeStatus}"
                                                                               orderid="${order.value.key.id}">
                                                                            </p>
                                                                        </div>
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
                                                                                       items="${order.value.value}">
                                                                                <tr>
                                                                                    <td class="text-right">${orderItem.id} </td>
                                                                                    <td class="text-left">${orderItem.title}</td>
                                                                                    <td class="text-center">
                                                                                        <c:set var="statOrderItem"
                                                                                               value="${orderItem.lendStatus}"
                                                                                        />
                                                                                        <div class="contSlItemStat"
                                                                                             orderid="${order.value.key.id}"
                                                                                             orderitemid="${orderItem.id}"
                                                                                             style="display: none">
                                                                                            <select class="custom-select slItemStat"
                                                                                                    id="slItemStat${orderItem.id}"
                                                                                                    orderid="${order.value.key.id}"
                                                                                                    orderitemid="${orderItem.id}">
                                                                                            </select>
                                                                                        </div>
                                                                                        <div class="contItemStat"
                                                                                             orderid="${order.value.key.id}"
                                                                                             orderitemid="${orderItem.id}">
                                                                                            <label class="badge lbItemStat"
                                                                                                   id="lbItemStat${orderItem.id}"
                                                                                                   orderid="${order.value.key.id}"
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
                                                     id="mdConfirmApprove${order.value.key.id}"
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
                                                                                id="btnDismissAppr${order.value.key.id}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnApproveOrder${order.value.key.id}"
                                                                                orderid="${order.value.key.id}"
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
                                                     id="mdConfirmReject${order.value.key.id}"
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
                                                                                id="btnDismissReject${order.value.key.id}"
                                                                                data-dismiss="modal">
                                                                            Cancel
                                                                        </button>
                                                                        <button type="submit"
                                                                                class="btn btn-primary float-right btnModalAppr"
                                                                                id="btnRejectOrder${order.value.key.id}"
                                                                                orderid="${order.value.key.id}"
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
<script src="https://cdn.datatables.net/searchpanes/1.3.0/js/dataTables.searchPanes.min.js"></script>
<script src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>
<%-- End plugin js for this page --%>
<%-- inject:js --%>
<script src="js/off-canvas.js"></script>
<script src="js/hoverable-collapse.js"></script>
<script src="js/template.js"></script>
<script src="js/settings.js"></script>
<script src="js/todolist.js"></script>
<script src="js/returnmanagement.js"></script>
<%-- endinject --%>
<%-- Custom js for this page--%>

<%-- End custom js for this page--%>
</body>
</html>
