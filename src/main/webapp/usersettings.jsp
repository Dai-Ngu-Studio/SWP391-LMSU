<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  Date: 6/7/2021
  Time: 12:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Setting</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/userAccountSetting.css">
    <link rel="shortcut icon" href="images/images/favicon.png"/>
    <%-- plugins:css --%>
    <link rel="stylesheet" href="vendors/feather/feather.css">
    <link rel="stylesheet" href="vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/css/vendor.bundle.base.css">
    <%-- endinject --%>
    <%-- Plugin css for this page --%>

    <%-- End plugin css for this page --%>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <%-- endinject --%>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<div class="p-5 bg-light">
    <div class="row">
        <div class="col-lg-3 grid-margin">
            <div class="card pb-3">
                <div class="card-body">
                    <h4 class="card-title">Settings</h4>
                    <div class="col-12">
                        <div class="list-group" id="list-tab" role="tablist">
                            <a class="list-group-item list-group-item-action active" id="list-information-list"
                               data-toggle="list" href="#list-information" role="tab" aria-controls="information"><i
                                    class="ti-user"></i> Account
                                Information</a>
                            <a class="list-group-item list-group-item-action" id="list-security-list" data-toggle="list"
                               href="#list-security" role="tab" aria-controls="security"><i class="ti-lock"></i>
                                Security</a>
                            <a class="list-group-item list-group-item-action" id="list-notifications-list"
                               data-toggle="list" href="#list-notifications" role="tab" aria-controls="notifications"><i
                                    class="ti-announcement"></i> Notifications</a>
                            <a class="list-group-item list-group-item-action" id="list-miscellaneous-list"
                               data-toggle="list" href="#list-miscellaneous" role="tab" aria-controls="miscellaneous"><i
                                    class="ti-agenda"></i> Book Borrowing</a>
                            <a class="list-group-item list-group-item-action" id="list-reserve-list"
                               data-toggle="list" href="#list-reserve" role="tab" aria-controls="miscellaneous"><i
                                    class="ti-package"></i> Wishlist</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-9 grid-margin stretch-card">
            <div class="card">
                <div class="col-12">
                    <div class="tab-content" id="nav-tabContent" style="border:none">
                        <%--Account Information tab--%>
                        <c:set var="profile" value="${sessionScope.LOGIN_USER}"/>
                        <c:set var="email_split" value="${fn:split(profile.email, '@')}"/>
                        <div class="tab-pane fade show active" id="list-information" role="tabpanel"
                             aria-labelledby="list-information-list">
                            <div class="card-body">
                                <h4 class="card-title">Account Information</h4>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" style="padding: 0 1.7rem;"
                                              id="basic-addon1">Member ID</span>
                                    </div>
                                    <input type="text" class="form-control" value="${profile.id}"
                                           aria-label="User ID" aria-describedby="basic-addon1"
                                           style="background-color: #fff" readonly/>
                                </div>
                                <c:if test="${not empty email_split}">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text px-5">Email</span>
                                        </div>
                                        <input type="text" class="form-control" value="${email_split[0]}"
                                               aria-label="Email Address" aria-describedby="basic-addon2"
                                               style="background-color: #fff" readonly/>
                                        <div class="input-group-append">
                                            <span class="input-group-text" style="padding: 0 1.3rem;"
                                                  id="basic-addon2">@fpt.edu.vn</span>
                                        </div>
                                    </div>
                                </c:if>

                                <form action="DispatchServlet">
                                    <input type="hidden" value="${profile.id}" name="pk">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Phone Number</span>
                                        </div>
                                        <input type="text" class="form-control" aria-label="Phone Number"
                                               value="${profile.phoneNumber}" pattern="^[0-9]{1,10}$"
                                               name="txtPhone"/>
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="submit" name="btAction"
                                                    value="Change Phone Number">
                                                Edit <i class="ti ti-pencil"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" style="padding: 0 1.3rem;">Semester No</span>
                                    </div>
                                    <input type="text" class="form-control" aria-label="Phone Number"
                                           value="${profile.semester}" readonly style="background-color: #fff"/>
                                </div>
                            </div>
                        </div>
                        <%--Security tab--%>
                        <div class="tab-pane fade" id="list-security" role="tabpanel"
                             aria-labelledby="list-security-list">
                            <div class="card-body">
                                <h4 class="card-title">Security</h4>
                                <div class="input-group mb-3">
                                    <form action="DispatchServlet" class="w-100">
                                        <input type="hidden" value="${profile.id}" name="pk">
                                        <div class="px-3">
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text" style="padding: 0 .8rem"
                                                          id="label-current-password">Current Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="Current Password"
                                                           aria-label="Current Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtCurrentPassword" value=""/>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text px-4"
                                                              id="label-new-password">New Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="New Password"
                                                           aria-label="New Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtNewPassword" value=""/>
                                                </div>
                                            </div>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text"
                                                          id="label-confirm-password">Confirm Password</span>
                                                    </div>
                                                    <input type="password" class="form-control"
                                                           placeholder="Confirm Password"
                                                           aria-label="Confirm Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtConfirmPassword" value=""/>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-primary" name="btAction"
                                                value="Change Password">
                                            Save changes
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <%--Notifications tab--%>
                        <div class="tab-pane fade" id="list-notifications" role="tabpanel"
                             aria-labelledby="list-notifications-list" style="color: black !important;">
                            <div class="row mb-1 align-items-center">
                                <div class="col-lg-5">
                                    Notify me about newest arrivals
                                </div>
                                <div class="col-lg-5">
                                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                        <label class="btn btn-secondary active">
                                            <input type="radio" name="options" id="offNewArrival"
                                                   autocomplete="off" checked/>
                                            Off
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="options" id="onNewArrival"
                                                   autocomplete="off"/>
                                            On
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row mb-1 align-items-center">
                                <div class="col-lg-5">
                                    Notify me about highest rated book of the week
                                </div>
                                <div class="col-lg-5">
                                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                        <label class="btn btn-secondary active">
                                            <input type="radio" name="options" id="offHighestRated"
                                                   autocomplete="off" checked/>Off
                                        </label>
                                        <label class="btn btn-secondary">
                                            <input type="radio" name="options" id="onHighestRated"
                                                   autocomplete="off"/>On
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="list-miscellaneous" role="tabpanel"
                             aria-labelledby="list-miscellaneous-list">
                            <div class="row">
                                <div class="col-12">
                                    <a class="float-right"
                                       href="ShowReturnCartServlet">
                                        <c:out value="View Return Cart "/><i class="fa fa-share-square-o"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="orderItemTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th class="text-right" style="width: 100px;">#</th>
                                            <th class="text-left" style="width: 73%;">NAME</th>
                                            <th class="text-center" style="width: 73%;">STATUS</th>
                                            <th class="text-center" style="width: 64px;">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--Need to add row to show status of item--%>
                                        <c:set var="orderItems" value="${requestScope.MEMBER_ORDER_ITEMS}"/>
                                        <c:forEach var="orderItem" items="${orderItems}" varStatus="counter">
                                            <tr class="odd">
                                                <form action="DispatchServlet">
                                                    <td class="sorting_1 text-center">${counter.count}</td>
                                                    <td class="text-left">
                                                            ${orderItem.title}
                                                    </td>
                                                        <%--
                                                            ITEM_CANCELLED = -1 ( does not show, only notify )
                                                            ITEM_PENDING = 0
                                                            ITEM_APPROVED = 1
                                                            ITEM_RECEIVED = 2
                                                            ITEM_RETURN_SCHEDULED = 3
                                                            ITEM_RETURNED = 4
                                                            ITEM_OVERDUE = 5
                                                            ITEM_OVERDUE_RETURN_SCHEDULED = 6
                                                            ITEM_OVERDUE_RETURNED = 7
                                                            ITEM_REJECTED = 8 ( does not show, only notify )
                                                            ITEM_LOST = 9
                                                            ITEM_RESERVED = 10 ( different tab )
                                                        --%>
                                                    <td class="text-center">
                                                        <c:choose>
                                                            <c:when test="${orderItem.lendStatus eq 0}">
                                                                <label class="badge badge-secondary">Pending</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 1}">
                                                                <label class="badge badge-info">Approved</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 2}">
                                                                <label class="badge badge-primary">Received</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 3}">
                                                                <label class="badge badge-warning">Scheduled</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 4}">
                                                                <label class="badge badge-success">Returned</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 5}">
                                                                <label class="badge badge-danger">Overdue</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 6}">
                                                                <label class="badge badge-warning">Scheduled</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 7}">
                                                                <label class="badge badge-success">Returned</label>
                                                            </c:when>
                                                            <c:when test="${orderItem.lendStatus eq 9}">
                                                                <label class="badge badge-dark">Missing</label>
                                                            </c:when>
                                                        </c:choose>
                                                    </td>
                                                </form>
                                                <form action="DispatchServlet">
                                                    <input type="hidden" name="bookPk" value="${orderItem.bookID}">
                                                    <input type="hidden" name="orderItemPk" value="${orderItem.id}">
                                                    <td class="text-center">
                                                        <div class="btn-group">
                                                            <button type="submit" class="btn btn-light"
                                                                    name="btAction" value="View Details">
                                                                <i class="fa fa-eye text-primary"></i>
                                                            </button>
                                                                <%--
                                                                Only allow to renew item with status:
                                                                    ITEM_RECEIVED = 2
                                                                    ITEM_OVERDUE = 5 (might need check business policy)
                                                                    --%>
                                                            <c:set var="renewalMap"
                                                                   value="${requestScope.RENEWAL_MAP_LIST}"/>

                                                            <c:choose>
                                                                <c:when test="${renewalMap.get(orderItem.id) <= 3}">
                                                                    <c:if test="${(orderItem.lendStatus eq 2)
                                                                    or (orderItem.lendStatus eq 5)}">
                                                                        <button type="button" class="btn btn-light"
                                                                                data-toggle="modal"
                                                                                data-target="#renewModal${orderItem.id}"
                                                                                title="Renew"
                                                                                data-original-title="Renew">
                                                                            <i class="fa fa-refresh text-primary"
                                                                               aria-hidden="true"></i>
                                                                        </button>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${renewalMap.get(orderItem.id) == 4}">
                                                                    <button type="button" class="btn btn-light"
                                                                            data-toggle="modal"
                                                                            data-target="#renewModal"
                                                                            title="Renew"
                                                                            data-original-title="Renew">
                                                                        <i class="fa fa-refresh text-primary"
                                                                           aria-hidden="true"></i>
                                                                    </button>
                                                                </c:when>
                                                            </c:choose>
                                                                <%--
                                                                Temporary return function,
                                                                need to add form similar to checkout.
                                                                Only allow to return item with status:
                                                                    ITEM_RECEIVED = 2
                                                                    ITEM_OVERDUE = 5
                                                                --%>

                                                            <c:if test="${(orderItem.lendStatus eq 2)
                                                                or (orderItem.lendStatus eq 5)}">
                                                                <button type="button" class="btn btn-light"
                                                                        data-toggle="modal"
                                                                        data-target="#returnModal${orderItem.id}"
                                                                        title="Return"
                                                                        data-original-title="Borrowing">
                                                                    <i class="fa fa-reply text-primary"
                                                                       aria-hidden="true"></i>
                                                                </button>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                </form>

                                                <c:set var="session" value="${sessionScope}"/>
                                                <c:if test="${not empty session}">
                                                    <c:set var="returnCart"
                                                           value="${sessionScope.RETURN_CART}"/>
                                                    <c:set var="cart" value="${sessionScope.MEMBER_CART}"/>
                                                    <c:set var="existedInReturnCart"
                                                           value="${returnCart.isExistedInCart(orderItem.id)}"/>
                                                    <c:set var="existedInCart"
                                                           value="${cart.isExistedInCart(orderItem.bookID)}"/>
                                                    <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
                                                </c:if>
                                                <div class="modal fade" id="returnModal${orderItem.id}"
                                                     tabindex="-1"
                                                     role="dialog">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button"
                                                                        class="close"
                                                                        data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${(not empty session) and (not empty user) and (existedInReturnCart)}">
                                                                    <div class="modal-body">
                                                                        This book is already in your cart.
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="modal-body">
                                                                        Do you want to return this book?
                                                                    </div>
                                                                    <form action="AddBookToReturnCartServlet">
                                                                        <input type="hidden" name="orderItemPk"
                                                                               value="${orderItem.id}">
                                                                        <input type="hidden" name="bookPk"
                                                                               value="${orderItem.bookID}">
                                                                        <div class="modal-footer">
                                                                            <button type="submit"
                                                                                    name="btAction"
                                                                                    value="Return Book"
                                                                                    class="btn btn-primary"
                                                                            >
                                                                                Yes
                                                                            </button>
                                                                            <button type="button"
                                                                                    class="btn btn-outline-primary"
                                                                                    data-dismiss="modal">
                                                                                Cancel
                                                                            </button>
                                                                        </div>
                                                                    </form>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="modal fade" id="renewModal" tabindex="-1" role="dialog">
                                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close"
                                                                        data-dismiss="modal">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                You have reached the renewal limit for this item.
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <form action="DispatchServlet">
                                                    <input type="hidden" name="orderItemPk" value="${orderItem.id}">
                                                    <div class="modal fade" id="renewModal${orderItem.id}"
                                                         tabindex="-1"
                                                         role="dialog"
                                                         aria-labelledby="ariaRenewModal${orderItem.id}"
                                                         aria-hidden="true">

                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLongTitle">
                                                                        Request renew book
                                                                    </h5>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                                <span
                                                                                        aria-hidden="true">&times;
                                                                                </span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">

                                                                    <div class="form-group row">
                                                                        <label class="col-sm-3 col-form-label">
                                                                            Reason
                                                                        </label>
                                                                        <div class="col-sm-9">
                                                                             <textarea
                                                                                     class="form-control"
                                                                                     name="txtReason"
                                                                                     rows="5">
                                                                             </textarea>
                                                                        </div>
                                                                    </div>

                                                                    <script>
                                                                        function validExtendDate(itemid) {
                                                                            let dateRow = $("#txtDate" + itemid);
                                                                            console.log(dateRow);
                                                                            var xhttp;
                                                                            xhttp = new XMLHttpRequest();
                                                                            let invalidText = `<div class="row" id="rowInvalidText">
                                                                                            <div class="col-12 text-left">
                                                                                                <small class="text-danger">
                                                                                                    Please don't choose a date that's below your current deadline
                                                                                                    (Current Deadline: ${orderItem.returnDeadline.toLocalDate()})
                                                                                                </small>
                                                                                            </div>
                                                                                        </div>`;
                                                                            xhttp.onreadystatechange = function () {
                                                                                if (this.readyState === 4 && this.status === 200) {
                                                                                    if (this.responseText.localeCompare('false') == 0) {
                                                                                        $("#rowExtendDate" + itemid).append(invalidText);
                                                                                        $("#btnSave"+itemid).attr('disabled', '').addClass("btn-secondary").removeClass("btn-primary");
                                                                                    }
                                                                                    else {
                                                                                        $("#rowInvalidText").remove();
                                                                                        $("#btnSave" + itemid).removeAttr('disabled').addClass("btn-primary").removeClass("btn-secondary");
                                                                                    }
                                                                                }
                                                                            }
                                                                            xhttp.open("GET", 'RenewalTimeValidationServlet?orderItemID=' + itemid + '&txtExtendDate=' + dateRow.val(), true);
                                                                            xhttp.send();
                                                                        }
                                                                    </script>

                                                                    <div class="form-group row" id="rowExtendDate${orderItem.id}">
                                                                        <label class="col-sm-3 col-form-label">
                                                                            Extend Date
                                                                        </label>
                                                                        <div class="col-sm-9">
                                                                            <input class="form-control"
                                                                                   type="date"
                                                                                   id="txtDate${orderItem.id}"
                                                                                   value=""
                                                                                   onchange="validExtendDate(${orderItem.id})"
                                                                                   name="txtExtendDate"
                                                                            >
                                                                        </div>
                                                                    </div>

                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="submit"
                                                                            class="btn btn-primary"
                                                                            name="btAction"
                                                                            value="Renew Book"
                                                                            id="btnSave${orderItem.id}"
                                                                    >
                                                                        Yes
                                                                    </button>
                                                                    <button type="button"
                                                                            class="btn btn-outline-primary"
                                                                            data-dismiss="modal">Cancel
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="list-reserve" role="tabpanel">

                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="reservedBookTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th class="text-right" style="width: 100px;">#</th>
                                            <th class="text-left" style="width: 73%;">NAME</th>
                                            <th class="text-center" style="width: 73%;">STATUS</th>
                                            <th class="text-center" style="width: 64px;">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--Need to add row to show status of item--%>
                                        <c:set var="reserveItems" value="${requestScope.MEMBER_RESERVE_ITEMS}"/>
                                        <c:set var="quantityMap" value="${requestScope.QUANTITY_MAP_LIST}"/>
                                        <c:forEach var="reserveItem" items="${reserveItems}" varStatus="counter">
                                            <tr class="odd">
                                                <form action="DispatchServlet">
                                                    <td class="sorting_1 text-center">${counter.count}</td>
                                                    <td class="text-left">
                                                            ${reserveItem.title}
                                                    </td>
                                                        <%--
                                                            ITEM_CANCELLED = -1 ( does not show, only notify )
                                                            ITEM_PENDING = 0
                                                            ITEM_APPROVED = 1
                                                            ITEM_RECEIVED = 2
                                                            ITEM_RETURN_SCHEDULED = 3
                                                            ITEM_RETURNED = 4
                                                            ITEM_OVERDUE = 5
                                                            ITEM_OVERDUE_RETURN_SCHEDULED = 6
                                                            ITEM_OVERDUE_RETURNED = 7
                                                            ITEM_REJECTED = 8 ( does not show, only notify )
                                                            ITEM_LOST = 9
                                                            ITEM_RESERVED = 10 ( different tab )
                                                        --%>
                                                    <td class="text-center">
                                                        <c:if test="${quantityMap.get(reserveItem.id) eq 0}">
                                                            <label class="badge badge-secondary">Out of Stock</label>
                                                        </c:if>
                                                        <c:if test="${quantityMap.get(reserveItem.id) gt 0}">
                                                            <label class="badge badge-primary">Available</label>
                                                        </c:if>
                                                    </td>
                                                </form>

                                                <c:set var="bookObj" value="${requestScope.BOOK_OBJECT}"/>
                                                <c:if test="${not empty session}">
                                                    <c:set var="cart" value="${sessionScope.MEMBER_CART}"/>
                                                    <c:set var="existedInCart"
                                                           value="${cart.isExistedInCart(reserveItem.bookID)}"/>
                                                    <c:set var="memberTotalActiveBorrows"
                                                           value="${sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS}"/>
                                                </c:if>
                                                <form action="DispatchServlet">
                                                    <input type="hidden" name="bookPk" value="${reserveItem.bookID}">
                                                    <td class="text-center">
                                                        <div class="btn-group">
                                                            <button type="submit" class="btn btn-light"
                                                                    name="btAction" value="View Details">
                                                                <i class="fa fa-eye text-primary"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-light"
                                                                    data-toggle="modal"
                                                                    data-target="#borrowModal${reserveItem.id}"
                                                                    title="Borrow"
                                                                    data-original-title="Borrowing">
                                                                <i class="ti-truck text-primary"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </form>

                                                <div class="modal fade"
                                                     id="borrowModal${reserveItem.id}"
                                                     tabindex="-1"
                                                     role="dialog"
                                                     aria-labelledby="ariaBorrowModal${reserveItem.id}"
                                                     aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
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
                                                            <c:choose>
                                                                <c:when test="${quantityMap.get(reserveItem.id) == 0}">
                                                                    <div class="modal-body">
                                                                        This book is currently out of stock.
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </c:when>

                                                                <c:when test="${(cart.cartQuantity ge 10)
                                                                or ((cart.cartQuantity + memberTotalActiveBorrows) ge 10)
                                                                or (memberTotalActiveBorrows ge 10)}">
                                                                    <div class="modal-body">
                                                                        You have reached the borrowing limit.
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button"
                                                                                class="btn btn-outline-primary"
                                                                                data-dismiss="modal">
                                                                            Close
                                                                        </button>
                                                                    </div>
                                                                </c:when>

                                                                <c:when test="${(orderItems.size() != 10)
                                                                and (quantityMap.get(reserveItem.id) > 0)}">
                                                                    <c:choose>
                                                                        <c:when test="${(not empty session)
                                                                        and (not empty user)
                                                                        and (existedInCart)}">
                                                                            <div class="modal-body">
                                                                                This book is already in your cart.
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button"
                                                                                        class="btn btn-outline-primary"
                                                                                        data-dismiss="modal">
                                                                                    Close
                                                                                </button>
                                                                            </div>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form action="AddBookToCartServlet">
                                                                                <input type="hidden" name="bookPk"
                                                                                       value="${reserveItem.bookID}">
                                                                                <input type="hidden"
                                                                                       name="memberPreviousAction"
                                                                                       value="user_settings:add_to_cart">
                                                                                <div class="modal-body">
                                                                                    Do you want to borrow this book?
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="submit"
                                                                                            name="btAction"
                                                                                            value="Borrow Book"
                                                                                            class="btn btn-primary">
                                                                                        Yes
                                                                                    </button>
                                                                                    <button type="button"
                                                                                            class="btn btn-outline-primary"
                                                                                            data-dismiss="modal">
                                                                                        No
                                                                                    </button>
                                                                                </div>
                                                                            </form>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
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

<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
