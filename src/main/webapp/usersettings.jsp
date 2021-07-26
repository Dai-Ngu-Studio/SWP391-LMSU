<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="js/usersettings.js"></script>
    <link rel="stylesheet" href="css/vertical-layout-light/style.css">
    <%-- endinject --%>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<c:set var="profile" value="${sessionScope.LOGIN_USER}"/>
<div class="p-5 bg-light">
    <c:if test="${not empty requestScope.MEMBER_UPDATE_SETTING_MESSAGE}">
        <div class="row txtUpdateSetting">
            <div class="col-4"></div>
            <div class="col-4">
                <div class="alert alert-success text-center">${requestScope.MEMBER_UPDATE_SETTING_MESSAGE}</div>
            </div>
        </div>
    </c:if>

    <c:if test="${requestScope.RETURN_SUCCESS}">
        <div class="row mt-2 txtUpdateSetting">
            <div class="col-lg-2"></div>
            <div class="col-lg-8 text-center">
                <div class="alert alert-success h6">
                    Return successful. Thank you for using our service. <br>
                </div>
            </div>
            <div class="col-lg-2"></div>
        </div>
    </c:if>
    <c:if test="${requestScope.RETURN_FAILED}">
        <div class="row mt-2 txtUpdateSetting">
            <div class="col-lg-2"></div>
            <div class="col-lg-8 text-center">
                <div class="alert alert-warning h6">
                    Return unsuccessful. Our system is under maintenance. <br>
                    Please try again later. Thank you for your understanding.
                </div>
            </div>
            <div class="col-lg-2"></div>
        </div>
    </c:if>
    <script>
        $(document).ready(function () {
            $('.txtUpdateSetting').on('click', function () {
                $('.txtUpdateSetting').fadeOut(300, function () {
                    $('.txtUpdateSetting').remove();
                });
            });
        });
    </script>

    <div class="row">
        <div class="col-lg-3 grid-margin">
            <div class="card pb-3">
                <div class="card-body">
                    <h4 class="card-title">Settings</h4>
                    <div class="col-12">
                        <div class="list-group" id="list-tab" role="tablist">
                            <a class="list-group-item list-group-item-action active" id="list-information-list"
                               data-toggle="list" href="#list-information" role="tab"><i class="ti-user">
                            </i> Account Information</a>
                            <c:if test="${profile.roleID eq '1'}">
                                <a class="list-group-item list-group-item-action" id="list-security-list"
                                   data-toggle="list" href="#list-security" role="tab"><i class="ti-lock">
                                </i>Security</a>
                            </c:if>
                            <a class="list-group-item list-group-item-action" id="list-notifications-list"
                               data-toggle="list" href="#list-notifications" role="tab">
                                <i class="ti-announcement"></i> Notifications</a>
                            <c:if test="${profile.roleID eq '4'}">
                                <a class="list-group-item list-group-item-action" id="list-miscellaneous-list"
                                   data-toggle="list" href="#list-miscellaneous" role="tab">
                                    <i class="ti-agenda"></i> Book Borrowing</a>
                                <a class="list-group-item list-group-item-action" id="list-reserve-list"
                                   data-toggle="list" href="#list-reserve" role="tab">
                                    <i class="ti-package"></i> Wishlist</a>
                                <a class="list-group-item list-group-item-action" id="list-order-list"
                                   data-toggle="list" href="#list-orders" role="tab">
                                    <i class="ti-shopping-cart-full"></i> Your Orders</a>
                                <a class="list-group-item list-group-item-action" id="list-order-list"
                                   data-toggle="list" href="#list-renewals" role="tab">
                                    <i class="ti-reload"></i> Renewal Requests</a>
                                <a class="list-group-item list-group-item-action" id="list-order-list"
                                   data-toggle="list" href="#list-penalties" role="tab">
                                    <i class="ti-flag-alt"></i> Penalties</a>
                            </c:if>
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
                        <c:set var="email_split" value="${fn:split(profile.email, '@')}"/>
                        <div class="tab-pane fade show active" id="list-information" role="tabpanel"
                             aria-labelledby="list-information-list">
                            <div class="card-body">
                                <h4 class="card-title">Account Information</h4>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"
                                              style="padding: 1.3rem 1.7rem; max-height: 2.4rem"
                                              id="basic-addon1">Member ID</span>
                                    </div>
                                    <input type="text" class="form-control" value="${profile.id}"
                                           aria-label="User ID" aria-describedby="basic-addon1"
                                           style="background-color: #fff; padding: 1.3rem 1.7rem" readonly/>
                                </div>
                                <c:if test="${not empty email_split}">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text px-5"
                                                  style="padding: 1.3rem 1.7rem; max-height: 2.4rem">Email</span>
                                        </div>
                                        <input type="text" class="form-control" value="${email_split[0]}"
                                               aria-label="Email Address" aria-describedby="basic-addon2"
                                               style="background-color: #fff; padding: 1.3rem 1.7rem" readonly/>
                                        <div class="input-group-append">
                                            <span class="input-group-text"
                                                  style="padding: 1.3rem 1.3rem;  max-height: 2.4rem"
                                                  id="basic-addon2">@fpt.edu.vn</span>
                                        </div>
                                    </div>
                                </c:if>

                                <form action="DispatchServlet">
                                    <input type="hidden" value="${profile.id}" name="pk">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"
                                                  style="padding: 1.3rem .8rem; max-height: 2.4rem">Phone Number</span>
                                        </div>
                                        <input type="text" class="form-control" aria-label="Phone Number"
                                               value="${profile.phoneNumber}" pattern="^[0-9]{10}$"
                                               name="txtPhone" style="padding: 1.3rem 1.7rem"/>
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="submit" name="btAction"
                                                    value="Change Phone Number"
                                                    style="padding: .6rem 1.5rem 2rem 1.5rem !important; max-height: 2.4rem">
                                                Edit <i class="ti ti-pencil"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"
                                              style="padding: 1.3rem 1.3rem; max-height: 2.4rem">Semester No</span>
                                    </div>
                                    <input type="text" class="form-control" aria-label="Phone Number"
                                           value="${profile.semester}" readonly
                                           style="background-color: #fff; padding: 1.3rem 1.7rem"/>
                                </div>
                            </div>
                        </div>
                        <%--Security tab--%>
                        <div class="tab-pane fade" id="list-security" role="tabpanel"
                             aria-labelledby="list-security-list">
                            <div class="card-body">
                                <h4 class="card-title">Security</h4>
                                <div class="input-group mb-3">
                                    <script>
                                        $(document).ready(function () {
                                            $('.currentPassInput').on("input", function () {
                                                $('.passwordError').removeClass('text-danger').addClass('text-muted');
                                                $('.currentPassInput').removeClass('is-invalid').addClass('is-valid');
                                                $.ajax({
                                                    method: 'POST',
                                                    url: 'ValidateChangePasswordServlet',
                                                    data: {
                                                        txtCurrentPassword: $('#inputCurrentPassword').val(),
                                                        txtNewPassword: $('#inputPasswordPattern').val(),
                                                        txtConfirmPassword: $('#inputConfirmPassword').val()
                                                    },
                                                    dataType: 'json',
                                                    success: function (responseJson) {
                                                        $.each(responseJson, function (key, value) {
                                                            $('#' + key)
                                                                .removeClass('text-muted')
                                                                .addClass('text-danger');
                                                            $('#' + value)
                                                                .removeClass('is-valid')
                                                                .addClass('is-invalid');
                                                        });

                                                        if ($.isEmptyObject(responseJson)) {
                                                            $('#btnChangePassword')
                                                                .removeAttr('disabled')
                                                                .removeClass('btn-secondary')
                                                                .addClass('btn-primary');
                                                        } else {
                                                            $('#btnChangePassword')
                                                                .attr('disabled', '')
                                                                .removeClass('btn-primary')
                                                                .addClass('btn-secondary');
                                                        }
                                                    }
                                                });
                                            });
                                        });
                                    </script>
                                    <form action="DispatchServlet" class="w-100" method="post">
                                        <input type="hidden" value="${profile.id}" name="pk">
                                        <div class="px-3">
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text" style="padding: 0 .8rem"
                                                          id="label-current-password">Current Password</span>
                                                    </div>
                                                    <input type="password" class="form-control currentPassInput"
                                                           placeholder="Current Password" id="inputCurrentPassword"
                                                           aria-label="Current Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtCurrentPassword" value=""/>
                                                </div>
                                            </div>
                                            <small id="errorCurrentPassword"
                                                   class="form-text text-muted passwordError">
                                                Password not match!
                                            </small>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text px-4"
                                                              id="label-new-password">New Password</span>
                                                    </div>
                                                    <input type="password" class="form-control currentPassInput"
                                                           placeholder="New Password" id="inputPasswordPattern"
                                                           aria-label="New Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtNewPassword" value=""/>
                                                </div>
                                            </div>
                                            <small id="errorPatternPassword"
                                                   class="form-text text-muted passwordError">
                                                Password pattern not valid!
                                            </small>
                                            <div class="row mb-1">
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                    <span class="input-group-text"
                                                          id="label-confirm-password">Confirm Password</span>
                                                    </div>
                                                    <input type="password" class="form-control currentPassInput"
                                                           placeholder="Confirm Password" id="inputConfirmPassword"
                                                           aria-label="Confirm Password"
                                                           aria-describedby="basic-addon1"
                                                           name="txtConfirmPassword" value=""/>
                                                </div>
                                            </div>
                                            <small id="errorConfirmPassword"
                                                   class="form-text text-muted passwordError">
                                                Confirm password not match!
                                            </small>
                                        </div>
                                        <button type="submit" class="btn btn-primary" name="btAction"
                                                value="Change Password" id="btnChangePassword" disabled>
                                            Save changes
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <%--Notifications tab--%>
                        <div class="tab-pane fade" id="list-notifications" role="tabpanel"
                             aria-labelledby="list-notifications-list" style="color: black !important;">
                            <form action="UpdateNotificationSettingServlet" method="POST" class="mx-0 my-0">
                                <div class="notificationButtons d-flex pb-4">
                                    <div class="col-4">
                                        Notify me about newest arrivals
                                    </div>
                                    <div>
                                        <input type="checkbox" id="toggly1"
                                        <c:if test="${profile.notifyArrival eq 'true'}">
                                               checked
                                        </c:if>
                                               name="newArrival" onchange='this.form.submit();'>
                                        <label for="toggly1"><i></i></label>
                                    </div>
                                </div>
                                <div class="notificationButtons d-flex pb-4">
                                    <div class="col-4">
                                        Notify me about highest rated books of the week
                                    </div>
                                    <div>
                                        <input type="checkbox" id="toggly2"
                                        <c:if test="${profile.notifyPopular eq 'true'}">
                                               checked
                                        </c:if>
                                               name="highestRated" onchange='this.form.submit();'>
                                        <label for="toggly2"><i></i></label>
                                    </div>
                                </div>
                            </form>
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
                                            <th class="text-right">#</th>
                                            <th class="text-left">BOOK</th>
                                            <th class="text-left">DEADLINE</th>
                                            <th class="text-center">STATUS</th>
                                            <th class="text-center">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--Need to add row to show status of item--%>
                                        <c:set var="orderItems" value="${requestScope.MEMBER_ORDER_ITEMS}"/>
                                        <c:forEach var="orderItem" items="${orderItems}" varStatus="counter">
                                            <tr class="odd">
                                                <form action="DispatchServlet">
                                                    <td class="text-right">${counter.count}</td>
                                                    <td class="text-left">
                                                            ${orderItem.title}
                                                    </td>
                                                    <td class="text-left">
                                                            ${orderItem.returnDeadline}
                                                    </td>
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
                                                            <c:set var="statusMap"
                                                                   value="${requestScope.RENEWAL_MAP_STATUS}"/>
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
                                                                <%--
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

                                                <form action="DispatchServlet" method="post">
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
                                                                        Request renewal book
                                                                    </h5>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                                <span
                                                                                        aria-hidden="true">&times;
                                                                                </span>
                                                                    </button>
                                                                </div>
                                                                <c:choose>
                                                                    <c:when test="${renewalMap.get(orderItem.id) <= 3
                                                                        and statusMap.get(orderItem.id) != 0}">
                                                                        <div class="modal-body">
                                                                            <div class="form-group row">
                                                                                <label class="col-sm-3 col-form-label">
                                                                                    Reason
                                                                                </label>
                                                                                <div class="col-sm-9">
                                                                                    <textarea class="form-control"
                                                                                              name="txtReason"
                                                                                              rows="5" required>
                                                                                    </textarea>
                                                                                </div>
                                                                            </div>
                                                                            <div class="form-group row"
                                                                                 id="rowExtendDate${orderItem.id}">
                                                                                <label class="col-sm-3 col-form-label">
                                                                                    Extend Date
                                                                                </label>
                                                                                <div class="col-sm-9">
                                                                                    <input class="form-control"
                                                                                           type="date"
                                                                                           id="txtDate${orderItem.id}"
                                                                                           value=""
                                                                                           onchange="validExtendDate(${orderItem.id})"
                                                                                           name="txtExtendDate" required
                                                                                    >
                                                                                </div>
                                                                            </div>
                                                                            <script>
                                                                                $(document).ready(function () {
                                                                                    let $rowExtDate${orderItem.id} = $("#rowExtendDate" + ${orderItem.id});
                                                                                    let $contInvalid${orderItem.id} = $('<div>')
                                                                                        .attr('id', 'contInvalid${orderItem.id}')
                                                                                        .addClass('row contInvalid').appendTo($rowExtDate${orderItem.id});
                                                                                    let $colInvalid${orderItem.id} = $('<div>')
                                                                                        .addClass('col-12 text-left').appendTo($contInvalid${orderItem.id});
                                                                                    let $txtInvalid${orderItem.id} = $('<small>')
                                                                                        .addClass('text-danger')
                                                                                        .text('The date chosen is before your current deadline (${orderItem.returnDeadline.toLocalDate()}). Please choose a later date.')
                                                                                        .appendTo($colInvalid${orderItem.id});
                                                                                    $("#contInvalid" + ${orderItem.id}).hide();

                                                                                    let $dateInvalid${orderItem.id} = $('<div>')
                                                                                        .attr('id', 'dateInvalid${orderItem.id}')
                                                                                        .addClass('row dateInvalid').appendTo($rowExtDate${orderItem.id});
                                                                                    let $colInvalid2${orderItem.id} = $('<div>')
                                                                                        .addClass('col-12 text-left').appendTo($dateInvalid${orderItem.id});
                                                                                    let $txtInvalid2${orderItem.id} = $('<small>')
                                                                                        .addClass('text-danger')
                                                                                        .text('Your renewal date must be within 7 days from your current deadline (${orderItem.returnDeadline.toLocalDate()}).')
                                                                                        .appendTo($colInvalid2${orderItem.id});
                                                                                    $("#dateInvalid" + ${orderItem.id}).hide();
                                                                                });

                                                                                function validExtendDate(itemid) {
                                                                                    let dateRow = $("#txtDate" + itemid);
                                                                                    var xhttp;
                                                                                    xhttp = new XMLHttpRequest();
                                                                                    xhttp.onreadystatechange = function () {
                                                                                        if (this.readyState === 4 && this.status === 200) {
                                                                                            if (this.responseText.localeCompare('invalid') == 0) {
                                                                                                $("#dateInvalid" + itemid).show();
                                                                                                $("#contInvalid" + itemid).hide();
                                                                                                $("#btnSave" + itemid).attr('disabled', '').addClass("btn-secondary").removeClass("btn-primary");
                                                                                            }
                                                                                            else if (this.responseText.localeCompare('false') == 0) {
                                                                                                $("#contInvalid" + itemid).show();
                                                                                                $("#dateInvalid" + itemid).hide();
                                                                                                $("#btnSave" + itemid).attr('disabled', '').addClass("btn-secondary").removeClass("btn-primary");
                                                                                            } else {
                                                                                                $("#dateInvalid" + itemid).hide();
                                                                                                $("#contInvalid" + itemid).hide();
                                                                                                $("#btnSave" + itemid).removeAttr('disabled').addClass("btn-primary").removeClass("btn-secondary");
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    xhttp.open("GET", 'RenewalTimeValidationServlet?orderItemID=' + itemid + '&txtExtendDate=' + dateRow.val(), true);
                                                                                    xhttp.send();
                                                                                }
                                                                            </script>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="submit"
                                                                                    class="btn btn-secondary"
                                                                                    name="btAction"
                                                                                    value="Renew Book" disabled
                                                                                    id="btnSave${orderItem.id}">
                                                                                Yes
                                                                            </button>
                                                                            <button type="button"
                                                                                    class="btn btn-outline-primary"
                                                                                    data-dismiss="modal">Cancel
                                                                            </button>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:when test="${renewalMap.get(orderItem.id) == 4}">
                                                                        <div class="modal-body">
                                                                            You have reached the renewal limit for this
                                                                            item.
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="button"
                                                                                    class="btn btn-secondary"
                                                                                    data-dismiss="modal">Close
                                                                            </button>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:when test="${statusMap.get(orderItem.id) == 0}">
                                                                        <div class="modal-body">
                                                                            The renewal request for this book is being
                                                                            process.
                                                                            Please wait patiently
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="button"
                                                                                    class="btn btn-secondary"
                                                                                    data-dismiss="modal">Close
                                                                            </button>
                                                                        </div>
                                                                    </c:when>
                                                                </c:choose>
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
                        <%--Wishlist--%>
                        <div class="tab-pane fade" id="list-reserve" role="tabpanel">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="reservedBookTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr role="row">
                                            <th class="text-right">#</th>
                                            <th class="text-left">BOOK</th>
                                            <th class="text-center">STATUS</th>
                                            <th class="text-center">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%--Need to add row to show status of item--%>
                                        <c:set var="reserveItems" value="${requestScope.MEMBER_RESERVE_ITEMS}"/>
                                        <c:set var="quantityMap" value="${requestScope.QUANTITY_MAP_LIST}"/>
                                        <c:forEach var="reserveItem" items="${reserveItems}" varStatus="counter">
                                            <tr class="odd">
                                                <form action="DispatchServlet">
                                                    <td class="text-right">${counter.count}</td>
                                                    <td class="text-left">
                                                            ${reserveItem.title}
                                                    </td>
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
                        <%--Order List--%>
                        <c:set var="orderList" value="${requestScope.MEMBER_ORDER_LIST}"/>
                        <div class="tab-pane fade dataTables_wrapper dt-bootstrap4 no-footer" id="list-orders"
                             role="tabpanel">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="ordersTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr>
                                            <th class="text-right">#</th>
                                            <th class="text-center">METHOD</th>
                                            <th class="text-left">ORDERED ON</th>
                                            <th class="text-center">STATUS</th>
                                            <th class="text-center">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="order" items="${orderList}" varStatus="orderCounter">
                                            <c:set var="lendMethod" value="${order.value.key.lendMethod}"/>
                                            <tr>
                                                <td class="text-right">${orderCounter.count}</td>
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
                                                <td class="text-center lbOrderStat"
                                                    id="lbOrderStat${order.value.key.id}"
                                                    orderid="${order.value.key.id}">
                                                    <label class="badge"
                                                           style="font-weight: 700 !important; font-size: 75% !important;"
                                                           activeStatus="${order.value.key.activeStatus}"
                                                           value="${order.value.key.activeStatus}"></label>
                                                </td>
                                                <td class="text-center">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-light"
                                                                data-toggle="modal"
                                                                data-target="#orderModal${order.value.key.id}">
                                                            <i class="fa fa-eye text-primary"></i>
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <c:forEach var="order" items="${orderList}">
                                        <%--Start: Order Details Form--%>
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
                                                            <label class="col-12 col-form-label">
                                                                Order ID
                                                            </label>
                                                            <div class=" col-12">
                                                                <input type="text"
                                                                       id="txtOrderID${order.value.key.id}"
                                                                       class="form-control"
                                                                       value="${order.value.key.id}"
                                                                       disabled/>
                                                            </div>
                                                            <label class=" col-12 col-form-label">
                                                                Order Date
                                                            </label>
                                                            <div class=" col-12">
                                                                <input type="text"
                                                                       class="form-control"
                                                                       value="${order.value.key.orderDate}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <c:if test="${not order.value.key.lendMethod}">
                                                            <div class="form-group row">
                                                                <label class=" col-12 col-form-label">
                                                                    Scheduled Time
                                                                </label>
                                                                <div class="1 col-12"
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
                                                                <label class=" col-12 col-form-label">
                                                                    Receiver Name
                                                                </label>
                                                                <div class=" col-12"
                                                                     orderid="${order.value.key.id}">
                                                                    <input type="text"
                                                                           class="form-control"
                                                                           value="${order.key.value.receiverName}"
                                                                           disabled/>
                                                                </div>
                                                                <label class=" col-12 col-form-label">
                                                                    Phone Number
                                                                </label>
                                                                <div class=" col-12"
                                                                     orderid="${order.value.key.id}">
                                                                    <input type="text"
                                                                           class="form-control"
                                                                           value="${order.key.value.phoneNumber}"
                                                                           disabled/>
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label class=" col-12 col-form-label">
                                                                    Street Address
                                                                </label>
                                                                <div class="1 col-12"
                                                                     orderid="${order.value.key.id}">
                                                                    <input type="text"
                                                                           class="form-control"
                                                                           value="${order.key.value.deliveryAddress1}"
                                                                           disabled/>
                                                                </div>
                                                            </div>
                                                            <c:if test="${(not empty fn:trim(order.key.value.deliveryAddress2))}">
                                                                <div class="form-group row">
                                                                    <label class=" col-12 col-form-label">
                                                                        Residence Address
                                                                    </label>
                                                                    <div class=" col-12"
                                                                         orderid="${order.value.key.id}">
                                                                        <input type="text"
                                                                               class="form-control"
                                                                               value="${order.key.value.deliveryAddress2}"
                                                                               disabled/>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                            <div class="form-group row">
                                                                <label class=" col-12 col-form-label">
                                                                    City
                                                                </label>
                                                                <div class=" col-12 txtCity"
                                                                     orderid="${order.value.key.id}">
                                                                    <input type="text"
                                                                           class="form-control"
                                                                           value="${order.key.value.cityName}"
                                                                           disabled/>
                                                                </div>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label class=" col-12 col-form-label">
                                                                    District
                                                                </label>
                                                                <div class=" col-12 txtDistrict"
                                                                     orderid="${order.value.key.id}">
                                                                    <input type="text"
                                                                           class="form-control"
                                                                           value="${order.key.value.districtName}"
                                                                           disabled/>
                                                                </div>
                                                                <label class=" col-12 col-form-label">
                                                                    Ward
                                                                </label>
                                                                <div class=" col-12 txtWard"
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
                                                            <label class="col-12 col-form-label">
                                                                Order Status
                                                            </label>
                                                            <div class=" col-12 lbOrderStat"
                                                                 id="pOrderStat${order.value.key.id}"
                                                                 orderid="${order.value.key.id}">
                                                                <p class="form-control"
                                                                   activeStatus="${order.value.key.activeStatus}"
                                                                   orderid="${order.value.key.id}">
                                                                </p>
                                                            </div>
                                                            <c:if test="${order.value.key.activeStatus eq 0}">
                                                                <label class="col-12 col-form-label lbCancelOrder"
                                                                       orderid="${order.value.key.id}">
                                                                    Cancel the order?
                                                                </label>
                                                                <div class="col-12 contModalCancelOrder"
                                                                     orderid="${order.value.key.id}">
                                                                    <button type="button"
                                                                            class="btn btn-block btn-light btn-sm rounded-0"
                                                                            data-toggle="modal"
                                                                            data-target="#mdConfirmCancelOrder${order.value.key.id}">
                                                                        <i class="fa fa-times-circle text-dark"
                                                                           style="font-size: 2rem !important;"></i>
                                                                    </button>
                                                                </div>
                                                            </c:if>
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
                                                                                       style="font-weight: 700 !important; font-size: 75% !important;"
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
                                        <%--End: Order Details Form--%>
                                        <div class="modal fade"
                                             id="mdConfirmCancelOrder${order.value.key.id}"
                                             tabindex="-1"
                                             style="overflow: hidden !important; ">
                                            <div class="modal-dialog modal-dialog-centered"
                                                 style="margin-top: 0px !important;">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            Cancel this Order
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
                                                                        id="btnDismissCnclOrder${order.value.key.id}"
                                                                        data-dismiss="modal">
                                                                    Cancel
                                                                </button>
                                                                <button type="submit"
                                                                        class="btn btn-primary float-right btnModalCnclOrder"
                                                                        id="btnCancelOrder${order.value.key.id}"
                                                                        orderid="${order.value.key.id}"
                                                                        ordermethod="${order.value.key.lendMethod}"
                                                                        role="cancelOrder">
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
                        <%--Renewal List--%>
                        <div class="tab-pane fade dataTables_wrapper dt-bootstrap4 no-footer" id="list-renewals"
                             role="tabpanel">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="renewalTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr>
                                            <th class="text-right">#</th>
                                            <th class="text-left">BOOK</th>
                                            <th class="text-left">EXTEND DATE</th>
                                            <th class="text-center">STATUS</th>
                                            <th class="text-center">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="renewalList" value="${requestScope.MEMBER_RENEWAL_LIST}"/>
                                        <c:forEach var="renewal" items="${renewalList}" varStatus="renewalCounter">
                                            <tr>
                                                <td class="text-right">${renewalCounter.count}</td>
                                                <td class="text-left">${renewal.item.title}</td>
                                                <td class="text-left">${renewal.requestedExtendDate}</td>
                                                <td class="text-center">
                                                    <label class="badge lbRenewalStat"
                                                           id="lbRenewalStat${renewal.renewalID}"
                                                           style="font-weight: 700 !important; font-size: 75% !important;"
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
                                    <c:forEach var="renewal" items="${renewalList}" varStatus="renewalCounter">
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
                                                            <label class="col-12 col-form-label">
                                                                Request ID
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       id="txtOrderID${renewal.renewalID}"
                                                                       class="form-control"
                                                                       value="${renewal.renewalID}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-12 col-form-label">
                                                                Book
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       id="txtBookID${renewal.item.title}"
                                                                       class="form-control"
                                                                       value="${renewal.item.title}"
                                                                       disabled/>
                                                            </div>
                                                            <label class="col-12 col-form-label">
                                                                Extend Date
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       class="form-control"
                                                                       value="${renewal.requestedExtendDate}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-12 col-form-label">
                                                                Reason
                                                            </label>
                                                            <div class="col-12">
                                                                        <textarea type="text" class="form-control"
                                                                                  disabled>${renewal.reason}</textarea>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row frmRenewalStat"
                                                             renewalid="${renewal.renewalID}">
                                                            <label class="col-12 col-form-label">
                                                                Renewal Status
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       class="form-control inpRenewalStat"
                                                                       id="inpRenewalStat${renewal.renewalID}"
                                                                       renewalid="${renewal.renewalID}"
                                                                       renewalStatus="${renewal.approvalStatus}"
                                                                       value="${renewal.approvalStatus}"
                                                                       disabled/>
                                                            </div>
                                                            <c:if test="${renewal.approvalStatus eq 0}">
                                                                <label class="col-12 col-form-label lbCancelRenewal"
                                                                       renewalid="${renewal.renewalID}">
                                                                    Cancel the request?
                                                                </label>
                                                                <div class="col-12 contModalCancelRenew"
                                                                     renewalid="${renewal.renewalID}">
                                                                    <button type="button"
                                                                            class="btn btn-block btn-light btn-sm rounded-0"
                                                                            data-toggle="modal"
                                                                            data-target="#mdConfirmCancelRenew${renewal.renewalID}">
                                                                        <i class="fa fa-times-circle text-dark"
                                                                           style="font-size: 2rem !important;"></i>
                                                                    </button>
                                                                </div>
                                                            </c:if>
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
                                             id="mdConfirmCancelRenew${renewal.renewalID}"
                                             tabindex="-1"
                                             style="overflow: hidden !important; ">
                                            <div class="modal-dialog modal-dialog-centered"
                                                 style="margin-top: 0px !important;">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            Cancel this Request
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
                                                                        id="btnDismissCnclRenew${renewal.renewalID}"
                                                                        data-dismiss="modal">
                                                                    Cancel
                                                                </button>
                                                                <button type="submit"
                                                                        class="btn btn-primary float-right btnModalCnclRenew"
                                                                        id="btnCancelRenewal${renewal.renewalID}"
                                                                        renewalid="${renewal.renewalID}"
                                                                        role="cancelRenewal">
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
                        <%--Penalty List--%>
                        <div class="tab-pane fade dataTables_wrapper dt-bootstrap4 no-footer" id="list-penalties"
                             role="tabpanel">
                            <div class="row">
                                <div class="col-sm-12">
                                    <table id="penaltyTable" class="table dataTable no-footer" role="grid">
                                        <thead>
                                        <tr>
                                            <th class="text-right">#</th>
                                            <th class="text-left">BOOK</th>
                                            <th class="text-left">DEADLINE</th>
                                            <th class="text-center">STATUS</th>
                                            <th class="text-center">ACTIONS</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="penaltyList" value="${requestScope.MEMBER_PENALTY_LIST}"/>
                                        <c:forEach var="penalty" items="${penaltyList}"
                                                   varStatus="counter">
                                            <tr>
                                                <td class="text-right">${counter.count}</td>
                                                <td class="text-left">
                                                        ${penalty.title}
                                                </td>
                                                <td class="text-left">
                                                        ${penalty.returnDeadline}
                                                </td>
                                                <td class="text-center">
                                                    <label class="badge lbPenaltyStat"
                                                           id="lbPenaltyStat${penalty.id}"
                                                           orderitemid="${penalty.id}"
                                                           penaltyStatus="${penalty.penaltyStatus}"
                                                           value="${penalty.penaltyStatus}"
                                                           style="font-weight: 700 !important; font-size: 75% !important;"></label>
                                                </td>
                                                <td class="text-center">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-light"
                                                                data-toggle="modal"
                                                                data-target="#penaltyModal${penalty.id}">
                                                            <i class="fa fa-eye text-primary"></i>
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <c:forEach var="penalty" items="${penaltyList}">
                                        <div class="modal fade"
                                             id="penaltyModal${penalty.id}"
                                             tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            Penalty Details
                                                        </h5>
                                                        <button type="button"
                                                                class="close"
                                                                data-dismiss="modal">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="form-group row">
                                                            <label class="col-12 col-form-label">
                                                                Order ID
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       id="txtOrderID${penalty.orderID}"
                                                                       class="form-control"
                                                                       value="${penalty.orderID}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-12 col-form-label">
                                                                Book
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       id="txtBookID${penalty.bookID}"
                                                                       class="form-control"
                                                                       value="${penalty.title}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-12 col-form-label">
                                                                Penalty Amount
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       class="form-control"
                                                                       value="${penalty.penaltyAmount}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row"
                                                             orderitemid="${penalty.id}">
                                                            <label class="col-12 col-form-label">
                                                                Penalty Status
                                                            </label>
                                                            <div class="col-12">
                                                                <input type="text"
                                                                       class="form-control inpPenaltyStat"
                                                                       id="inpPenaltyStat${penalty.id}"
                                                                       orderitemid="${penalty.id}"
                                                                       penaltyStatus="${penalty.penaltyStatus}"
                                                                       value="${penalty.penaltyStatus}"
                                                                       disabled/>
                                                                <select class="form-control custom-select slPenaltyStat"
                                                                        id="slPenaltyStat${penalty.id}"
                                                                        orderitemid="${penalty.id}"
                                                                        style="display: none">
                                                                </select>
                                                            </div>
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
                                    </c:forEach>
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
