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
    <jsp:include page="adminheader.jsp"/>
    <%-- partial --%>
    <div class="container-fluid page-body-wrapper">
        <%-- partial --%>
        <jsp:include page="sidebar.jsp"></jsp:include>
        <%-- partial:../../partials/_sidebar.html --%>
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
                                                    <th class="text-right">#</th>
                                                    <th class="text-center">METHOD</th>
                                                    <th class="text-left">ORDERED ON</th>
                                                    <th class="text-left">BORROWER</th>
                                                    <th class="text-center">STATUS</th>
                                                    <th class="text-center">ACTIONS</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <%--Map<Pair<DirectOrderObj, DeliveryOrderObj>, Pair<OrderObj, List<OrderItemObj>>>--%>
                                                <b id="userRole" userRole="${sessionScope.LOGIN_USER.roleID}"
                                                   hidden></b>
                                                <c:set var="jspUserRole" value="${sessionScope.LOGIN_USER.roleID}"/>
                                                <c:set var="orderList" value="${requestScope.ORDER_LIST}"/>
                                                <c:forEach var="order" items="${orderList}"
                                                           varStatus="counter">
                                                    <c:set var="lendMethod" value="${order.value.key.lendMethod}"/>
                                                    <%--Return orders are not shown in this page--%>
                                                    <c:if test="${not order.key.key.returnOrder}">
                                                        <tr>
                                                            <td class="text-right">${counter.count}</td>
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
                                                                            Order ID
                                                                        </label>
                                                                        <div class="col-lg-5 col-12">
                                                                            <input type="text"
                                                                                   id="txtOrderID${order.value.key.id}"
                                                                                   class="form-control"
                                                                                   value="${order.value.key.id}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Order Date
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
                                                                        <c:set var="staffID"
                                                                               value="${order.key.key.librarianID}"/>
                                                                        <c:set var="staffName"
                                                                               value="${order.key.key.librarianName}"/>
                                                                    </c:if>
                                                                    <c:if test="${order.value.key.lendMethod}">
                                                                        <c:set var="staffID"
                                                                               value="${order.key.value.managerID}"/>
                                                                        <c:set var="staffName"
                                                                               value="${order.key.value.managerName}"/>
                                                                    </c:if>
                                                                    <c:if test="${empty staffID}">
                                                                        <c:set var="staffID" value="N/A"/>
                                                                        <c:set var="staffName" value="N/A"/>
                                                                    </c:if>
                                                                    <div class="form-group row">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Staff ID
                                                                        </label>
                                                                        <div class="col-lg-5 col-12 frmStaffID"
                                                                             orderid="${order.value.key.id}">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${staffID}"
                                                                                   disabled/>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Staff Name
                                                                        </label>
                                                                        <div class="col-lg-5 col-12 frmStaffName"
                                                                             orderid="${order.value.key.id}">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   value="${staffName}"
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
                                                                                       value="${order.key.value.cityName}"
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
                                                                                       value="${order.key.value.districtName}"
                                                                                       disabled/>
                                                                            </div>
                                                                            <label class="col-lg-1 col-12 col-form-label">
                                                                                Ward
                                                                            </label>
                                                                            <div class="col-lg-5 col-12 txtWard"
                                                                                 orderid="${order.value.key.id}">
                                                                                <input type="text"
                                                                                       class="form-control"
                                                                                       value="${order.key.value.wardName}"
                                                                                       disabled/>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                    <div class="form-group row frmOrderStat"
                                                                         orderid="${order.value.key.id}">
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Order Status
                                                                        </label>
                                                                        <div class="col-lg-5 col-12 lbOrderStat"
                                                                             id="pOrderStat${order.value.key.id}"
                                                                             orderid="${order.value.key.id}">
                                                                            <p class="form-control"
                                                                               activeStatus="${order.value.key.activeStatus}"
                                                                               orderid="${order.value.key.id}">
                                                                            </p>
                                                                        </div>
                                                                        <label class="col-lg-1 col-12 col-form-label">
                                                                            Approval Status
                                                                        </label>

                                                                        <c:choose>
                                                                            <c:when test="${order.value.key.activeStatus eq 0}">
                                                                                <%--isManager&Delivery || isLibrarian&Direct--%>
                                                                                <c:if test="${((order.value.key.lendMethod eq true) and (jspUserRole eq '2'))
                                                                                or ((order.value.key.lendMethod eq false) and (jspUserRole eq '3'))}">
                                                                                    <div class="col-3 pr-0 contModalApprove"
                                                                                         orderid="${order.value.key.id}">
                                                                                        <button type="button"
                                                                                                class="btn btn-block btn-light btn-sm rounded-0"
                                                                                                data-toggle="modal"
                                                                                                data-target="#mdConfirmApprove${order.value.key.id}"
                                                                                                style="border-top-left-radius: 1rem !important;
                                                                                    border-bottom-left-radius: 1rem !important">
                                                                                            <h3 class="fa fa-check-circle text-success"></h3>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="col-2 pl-0 contModalReject"
                                                                                         orderid="${order.value.key.id}">
                                                                                        <button type="button"
                                                                                                class="btn btn-block btn-light btn-sm rounded-0"
                                                                                                data-toggle="modal"
                                                                                                data-target="#mdConfirmReject${order.value.key.id}"
                                                                                                style="border-top-right-radius: 1rem !important;
                                                                                    border-bottom-right-radius: 1rem !important">
                                                                                            <h3 class="fa fa-times-circle text-danger"></h3>
                                                                                        </button>
                                                                                    </div>
                                                                                </c:if>
                                                                                <c:if test="${((order.value.key.lendMethod eq false) and (jspUserRole eq '2'))
                                                                                or ((order.value.key.lendMethod eq true) and (jspUserRole eq '3'))}">
                                                                                    <div class="col-lg-5 col-12">
                                                                                        <div class="btn btn-block btn-outline-secondary btn-sm bg-white"
                                                                                             disabled>
                                                                                            <h3 class="fa fa-pause-circle text-secondary"></h3>
                                                                                        </div>
                                                                                    </div>
                                                                                </c:if>
                                                                            </c:when>
                                                                            <c:when test="${(order.value.key.activeStatus eq -1)
                                                                            or (order.value.key.activeStatus eq 5)}">
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
                                                                        <%--CANCELLED is either by member or system do 1 day after scheduled date--%>
                                                                        <%--Some to-do: --%>
                                                                        <%--Add Status Lock button to prevent accidental update--%>
                                                                    <c:if test="${((order.value.key.lendMethod eq true) and (jspUserRole eq '2'))
                                                                                or ((order.value.key.lendMethod eq false) and (jspUserRole eq '3'))
                                                                                or ((order.value.key.activeStatus eq 2) or (order.value.key.activeStatus eq 4))}">
                                                                        <div class="row">
                                                                            <div class="col-12 text-center">
                                                                                <div class="btn-group groupBtnEdit"
                                                                                     orderID="${order.value.key.id}">
                                                                                    <button type="button"
                                                                                            class="btn btn-light"
                                                                                            orderID="${order.value.key.id}">
                                                                                        <h4 class="fa fa-pencil text-primary"></h4>
                                                                                    </button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <%--Place to swap buttons in and out of view--%>
                                                                        <div class="row storageBtn"
                                                                             id="hiddenBtnStorage${order.value.key.id}"
                                                                             orderID="${order.value.key.id}"
                                                                             hidden>
                                                                            <button type="button" class="btn btn-light"
                                                                                    orderID="${order.value.key.id}"
                                                                                    role="confirmEdit">
                                                                                <h4 class="fa fa-check text-success"></h4>
                                                                            </button>
                                                                            <button type="button" class="btn btn-light"
                                                                                    orderID="${order.value.key.id}"
                                                                                    role="cancelEdit">
                                                                                <h4 class="fa fa-times text-danger"></h4>
                                                                            </button>
                                                                        </div>
                                                                    </c:if>
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
                                                <c:if test="${((order.value.key.lendMethod eq true) and (jspUserRole eq '2'))
                                                                                or ((order.value.key.lendMethod eq false) and (jspUserRole eq '3'))}">
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
                                                                                    ordermethod="${order.value.key.lendMethod}"
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
                                                                                    ordermethod="${order.value.key.lendMethod}"
                                                                                    role="rejectOrder">
                                                                                Yes
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
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
<script src="js/bookrentalmanagement.js"></script>
<%-- endinject --%>
<%-- Custom js for this page--%>

<%-- End custom js for this page--%>
</body>
</html>
