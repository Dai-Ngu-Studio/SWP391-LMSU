<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <%-- Fontawsome CDN --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <script src="js/iconpro.js"></script>
</head>
<body>
<c:set var="role" value="${sessionScope.LOGIN_USER.roleID}"/>
<c:if test="${role eq '4' or role eq 'null'}">
    <c:redirect url="IndexServlet"></c:redirect>
</c:if>

<c:if test="${role eq '3' or role eq '2'}">
    <c:redirect url="ShowDashboardServlet"></c:redirect>
</c:if>

<c:if test="${role eq '1'}">
    <div class="container-scroller">
            <%-- partial:../../partials/_navbar.html --%>
        <jsp:include page="adminheader.jsp"/>
            <%-- partial --%>
        <div class="container-fluid page-body-wrapper">
                <%-- partial --%>
            <jsp:include page="sidebar.jsp"/>
                <%-- partial:../../partials/_sidebar.jsp --%>
                <%-- partial --%>
            <div class="main-panel">
                <div class="content-wrapper">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title">User Management</h4>
                            <c:if test="${not empty requestScope.DELETED_USER or not empty requestScope.ADD_DUPLICATE}">
                                <div class="alert alert-danger text-center">
                                        ${requestScope.DELETED_USER}
                                        ${requestScope.ADD_DUPLICATE}
                                </div>
                            </c:if>

                            <c:if test="${not empty requestScope.PASSWORD_ADMIN or not empty requestScope.DELETED_USER or not empty requestScope.LOGGING_IN_USER or not empty requestScope.ADD_DUPLICATE}">
                                <div class="alert alert-danger text-center">
                                        ${requestScope.LOGGING_IN_USER}
                                        ${requestScope.DELETED_USER}
                                        ${requestScope.ADD_DUPLICATE}
                                        ${requestScope.PASSWORD_ADMIN}
                                </div>
                            </c:if>

                            <c:set var="error" value="${requestScope.CREATE_ERROR}"/>

                            <c:if test="${not empty error.idError}">
                                <div class="alert alert-danger text-center">
                                        ${error.idError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.nameError}">
                                <div class="alert alert-danger text-center">
                                        ${error.nameError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.emailError}">
                                <div class="alert alert-danger text-center">
                                        ${error.emailError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.phoneNumberError}">
                                <div class="alert alert-danger text-center">
                                        ${error.phoneNumberError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.passwordError}">
                                <div class="alert alert-danger text-center">
                                        ${error.passwordError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.roleIDError}">
                                <div class="alert alert-danger text-center">
                                        ${error.roleIDError}
                                </div>
                            </c:if>
                            <c:if test="${not empty error.semesterError}">
                                <div class="alert alert-danger text-center">
                                        ${error.semesterError}
                                </div>
                            </c:if>

                            <c:set var="updateError" value="${requestScope.UPDATE_ERROR}"/>
                            <c:if test="${not empty updateError.nameError}">
                                <div class="alert alert-danger text-center">
                                        ${updateError.nameError}
                                </div>
                            </c:if>
                            <c:if test="${not empty updateError.phoneNumberError}">
                                <div class="alert alert-danger text-center">
                                        ${updateError.phoneNumberError}
                                </div>
                            </c:if>
                            <c:if test="${not empty updateError.semesterError}">
                                <div class="alert alert-danger text-center">
                                        ${updateError.semesterError}
                                </div>
                            </c:if>
                            <c:if test="${not empty updateError.activeStatusError}">
                                <div class="alert alert-danger text-center">
                                        ${updateError.roleIDError}
                                </div>
                            </c:if>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <div class="dataTables_wrapper dt-bootstrap4 no-footer">
                                            <div class="row">
                                                <div class="table-responsive">
                                                    <table id="user-datatable"
                                                           class="table table-hover dataTable no-footer my-2">
                                                        <thead>
                                                        <tr role="row">
                                                            <th class="text-right" style="width: 0px;">#</th>
                                                            <th style="width: 96px;">USER ID</th>
                                                            <th style="width: 56px;">NAME</th>
                                                            <th style="width: 67px;">EMAIL</th>
                                                            <th class="text-center" style="width: 56px;">STATUS</th>
                                                            <th class="text-center" style="width: 64px;">ACTIONS</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:set var="memberList" value="${requestScope.MEMBER_LIST}"/>
                                                        <c:forEach var="member" items="${memberList}"
                                                                   varStatus="counter">
                                                            <tr>
                                                                <td class="text-right">${counter.count}</td>
                                                                <td class="text-left">${member.id}</td>
                                                                <td class="text-left">${member.name}</td>
                                                                <td class="text-left">${member.email}</td>
                                                                <td class="text-center">
                                                                    <c:if test="${member.delete eq 'false'}">
                                                                        <c:if test="${member.activeStatus eq 'false'}">
                                                                            <span class="badge badge-warning text-center">Inactive</span>
                                                                        </c:if>
                                                                        <c:if test="${member.activeStatus eq 'true'}">
                                                                            <span class="badge badge-success text-center">Active</span>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${member.delete eq 'true'}">
                                                                        <span class="badge badge-danger text-center">Deleted</span>
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
                                                                                                             onerror="this.onerror=null; this.src='${member.profilePicturePath}';"
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
                                                                                                         onerror="this.onerror=null; this.src='${member.profilePicturePath}';"
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
                                                                                                               onchange="readURL(this, 'coverPictureUpdate${member.id}');"
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
                                                                                                           minlength="2"
                                                                                                           maxlength="30"
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
                                                                                                           class="form-control"
                                                                                                           name="txtUpdateMemberSemester"
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
                                                                                                    <select name="txtUpdateActiveStatus"
                                                                                                            class="pl-3">
                                                                                                        <c:if test="${member.activeStatus eq 'false'}">
                                                                                                            <option value="0"
                                                                                                                    selected>
                                                                                                                Inactive
                                                                                                            </option>
                                                                                                            <option value="1">
                                                                                                                Active
                                                                                                            </option>
                                                                                                        </c:if>
                                                                                                        <c:if test="${member.activeStatus eq 'true'}">
                                                                                                            <option value="0">
                                                                                                                Inactive
                                                                                                            </option>
                                                                                                            <option value="1"
                                                                                                                    selected>
                                                                                                                Active
                                                                                                            </option>
                                                                                                        </c:if>
                                                                                                    </select>
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
                                                                            <c:if test="${member.delete eq 'false'}">
                                                                                <button type="button"
                                                                                        class="btn btn-light"
                                                                                        data-toggle="modal"
                                                                                        title="Delete"
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
                                                                                                Do you want to delete
                                                                                                this
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
                                                                            </c:if>
                                                                            <c:if test="${member.delete eq 'true'}">
                                                                                <button type="button"
                                                                                        class="btn btn-light"
                                                                                        data-toggle="modal"
                                                                                        title="Undelete"
                                                                                        data-target="#undeleteModal${member.id}"
                                                                                        data-original-title="Undelete">
                                                                                    <i class="fa fa-undo text-primary"></i>
                                                                                </button>
                                                                                <div class="modal fade"
                                                                                     id="undeleteModal${member.id}"
                                                                                     tabindex="-1" role="dialog"
                                                                                     aria-labelledby="ariaUndeleteModal${member.id}"
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
                                                                                                Do you want to undelete
                                                                                                this
                                                                                                user?
                                                                                            </div>
                                                                                            <div class="modal-footer">
                                                                                                <button type="submit"
                                                                                                        name="btAction"
                                                                                                        value="Undelete User"
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
                                                                            </c:if>
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
    <%-- endinject -->
    <!-- Plugin js for this page --%>
    <script src="vendors/datatables.net/jquery.dataTables.js"></script>
    <script src="vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
    <%-- End plugin js for this page --%>
    <%-- inject:js --%>
    <script src="js/off-canvas.js"></script>
    <script src="js/hoverable-collapse.js"></script>
    <script src="js/template.js"></script>
    <script src="js/settings.js"></script>
    <script src="js/todolist.js"></script>
    <%-- endinject --%>
    <%-- Custom js for this page--%>
    <script src="js/membermanagement.js"></script>
    <script>
        $(document).ready(function () {
            $('#user-datatable').DataTable({bFilter: false});

            let SearchAdd = `
        <div class="d-flex justify-content-end">
            <form action="DispatchServlet" class="d-flex">
                <input type="search" class="form-control"
                       id="searchBox" placeholder="Search"
                       name="txtSearchValue" value="${param.txtSearchValue}"
                       aria-controls="order-listing"
                       style="border-radius: 5px 0 0 5px"
                >
                    <button class="btn btn-primary mr-2" type="submit"
                            name="btAction" value="Search User" title="Search user"
                            style="border-radius: 0 5px 5px 0">
                        <i class="fa fa-search"></i>
                    </button>
            </form>

            <form action="DispatchServlet" method="POST">
                <button class="btn btn-primary" type="button"
                        style="border-radius: 5px"
                        data-toggle="modal"
                        data-target="#AddUserModal"
                        title="Add user">
                    <i class="fa fa-plus"></i>
                </button>
                <div class="modal fade" id="AddUserModal"
                     tabindex="-1"
                     role="dialog"
                     aria-labelledby="AddBookModalLongTitle"
                     aria-hidden="true">
                    <div class="modal-dialog"
                         style="margin-top: 30px" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title"
                                    id="AddBookModalLongTitle">
                                    Add user
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
                                        User ID <span class="required-field"> *</span>
                                    </label>
                                    <div class="col-sm-9">
                                        <input type="text" required
                                               minlength="8" maxlength="8"
                                               pattern="^[a-zA-Z]{2,3}[0-9]{5,6}$"
                                               class="form-control"
                                               name="txtUserID" value=""
                                        >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-3 col-form-label">
                                        Name <span class="required-field"> *</span>
                                    </label>
                                    <div class="col-sm-9">
                                        <input type="text" required
                                               minlength="2" maxlength="30"
                                               class="form-control"
                                               name="txtUserName" value=""
                                        >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-3 col-form-label">
                                        Semester No. <span class="required-field"> *</span>
                                    </label>
                                    <div class="col-sm-9">
                                        <input type="number"
                                               min="0" max="9"
                                               class="form-control" required
                                               name="txtSemester" value=""
                                        >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-3 col-form-label">
                                        Email <span class="required-field"> *</span>
                                    </label>
                                    <div class="col-sm-9">
                                        <input type="email" required
                                               class="form-control" required
                                               name="txtEmail" value=""
                                        >
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-3 col-form-label">
                                        Phone number
                                    </label>
                                    <div class="col-sm-9">
                                        <input type="tel"
                                               class="form-control"
                                               name="txtPhoneNumber" value=""
                                        >
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary"
                                        name="btAction" value="Add User"
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
        </div>
    `;

            $('#user-datatable_wrapper').children().eq(0).children().eq(1).append(SearchAdd);

        });
    </script>
    <%-- End custom js for this page--%>
</c:if>
</body>
</html>
