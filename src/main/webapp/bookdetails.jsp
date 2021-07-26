<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="bookObj" value="${requestScope.BOOK_OBJECT}"/>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>${bookObj.title}</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<c:set var="session" value="${sessionScope}"/>
<c:set var="user" value="${sessionScope.LOGIN_USER}"/>
<%--Actual Body--%>
<div class="bg-light">
    <%--Start: Book Section--%>
    <div class="row pt-5">
        <%--Start: Empty Column--%>
        <div class="col-lg-2"></div>
        <%--End: Empty Column--%>
        <%--Start: Left Column--%>
        <div class="col-lg-3">
            <%--Start: Book Cover Image--%>
            <div class="text-center">
                <img src="${pageContext.request.contextPath}/image/${bookObj.coverPath}"
                     class="rounded img-fluid img-thumbnail"
                     onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"
                />
            </div>
            <%--End: Book Cover Image--%>
            <%--Start: Book Metadata--%>
            <div class="card mt-2">
                <div class="card-body">
                    <div class="card-title">Book Information</div>
                    <ul class="list-group">
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Format</strong></div>
                                <div class="col-lg-6">Physical</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Author</strong></div>
                                <div class="col-lg-6">
                                    <c:forEach var="author" items="${bookObj.authors}" varStatus="authorCounter">
                                        <a href="<c:url value='ShowAuthorBookServlet?authorPk=${author.key}'/>"
                                           style="color: #748DDB">
                                                ${author.value}
                                        </a>
                                        <c:if test="${not authorCounter.last}">
                                            <c:out value=", "/>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Publisher</strong></div>
                                <div class="col-lg-6">${bookObj.publisher}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>Publication Date</strong></div>
                                <div class="col-lg-6">${bookObj.publishDate}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>ISBN-13</strong></div>
                                <div class="col-lg-6">${bookObj.isbnThirteen}</div>
                            </div>
                        </li>
                        <li class="list-group-item mt-0 mb-0">
                            <div class="row">
                                <div class="col-lg-6"><strong>ISBN-10</strong></div>
                                <div class="col-lg-6">${bookObj.isbnTen}</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <%--End: Book Metadata--%>
        </div>
        <%--End: Left Column--%>
        <%--Start: Right Column--%>
        <div class="col-lg-5">
            <div class="card">
                <div class="card-body">
                    <span class="card-title mb-0 pr-2">${bookObj.title}</span>
                    <%--Start: Book Rating--%>
                    <span class="card-text" style="color: #748CDA;">
                        <c:set var="bookAvgRating" value="${bookObj.avgRating}"/>
                        <c:if test="${bookAvgRating gt 5}">
                            <c:set var="bookAvgRating" value="${5}"/>
                        </c:if>
                        <%--Get numbers before and after decimal plate--%>
                        <c:set var="integralRating" value="${fn:substringBefore(bookAvgRating,'.')}"/>
                        <c:set var="fractionalRating" value="${bookAvgRating mod 1}"/>
                        <%--If rating is not 0, print stars--%>
                        <c:if test="${bookAvgRating ne 0}">
                            <%--Print stars until reach integral number of stars--%>
                            <c:forEach begin="${1}" end="${integralRating}">
                                <i class="fa fa-star" aria-hidden="true"></i>
                            </c:forEach>
                            <%--If there are numbers after decimal plate and rating is not 5--%>
                            <c:if test="${(fractionalRating ne 0) and (integralRating ne 5)}">
                                <%--ge: greater or equal--%>
                                <%--lt: less than--%>
                                <%--ne: not equal--%>
                                <%--Print half star if fraction greater than or equal to 0.5--%>
                                <c:if test="${(fractionalRating ge 0.5)}">
                                    <i class="fa fa-star-half-o" aria-hidden="true"></i>
                                </c:if>
                                <%--Print empty star if fraction less than 0.5--%>
                                <c:if test="${(fractionalRating lt 0.5)}">
                                    <i class="fa fa-star-o" aria-hidden="true"></i>
                                </c:if>
                            </c:if>
                            <%--If there are no numbers after decimal plate and rating is not 5--%>
                            <c:if test="${(fractionalRating eq 0) and (integralRating ne 5)}">
                                <%--Print remaining empty stars--%>
                                <c:forEach begin="${integralRating}" end="${4}">
                                    <i class="fa fa-star-o" aria-hidden="true"></i>
                                </c:forEach>
                            </c:if>
                        </c:if>
                        <%--If rating is 0, print five empty stars--%>
                        <c:if test="${bookAvgRating eq 0}">
                                <div class="badge badge-secondary text-wrap">
                                    Not rated
                                </div>
                        </c:if>
                        </span>
                    <%--End: Book Rating--%>
                    <%--Start: Author--%>
                    <div class="card-text mt-2">
                        <c:out value="By "/>
                        <c:forEach var="author" items="${bookObj.authors}" varStatus="authorCounter">
                            <a href="<c:url value='ShowAuthorBookServlet?authorPk=${author.key}'/>"
                               class="badge badge-secondary">${author.value}</a>
                            <c:if test="${not authorCounter.last}">
                                <c:out value=" ,"/>
                            </c:if>
                        </c:forEach>
                    </div>
                    <%--End: Author--%>
                    <%--Start: Description--%>
                    <div class="card-title mt-2 mb-0">Description</div>
                    <div class="card-text">
                        <c:out value="${bookObj.description}"/>
                    </div>
                    <%--End: Description--%>
                </div>
            </div>
            <%--Start: Book Options Section--%>
            <div class="card mt-3">
                <div class="card-body">
                    <div class="row text-center">
                        <div class="col-lg-3"></div>
                        <div class="col-lg-6">
                            <c:if test="${not empty session}">
                                <c:set var="cart" value="${sessionScope.MEMBER_CART}"/>
                                <c:set var="existedInCart" value="${cart.isExistedInCart(bookObj.id)}"/>
                                <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
                            </c:if>
                            <c:choose>
                                <%--Book existed in cart--%>
                                <c:when test="${(not empty session) and (not empty user) and (existedInCart)}">
                                    <div class="row">
                                        <div class="col-12 text-center my-2">
                                            This book is already in your cart.
                                        </div>
                                    </div>
                                    <form action="RemoveFromCartServlet" class="my-1">
                                        <div class="row">
                                            <div class="col-2"></div>
                                            <div class="col-8 text-center">
                                                <input type="hidden" name="bookPk" value="${bookObj.id}">
                                                <input type="hidden" name="isBrowsingBooks" value="true">
                                                <button type="submit" class="btn btn-danger btn-block"
                                                        name="btAction" value="RemoveFromCart">
                                                    <i class="fa fa-minus-circle" aria-hidden="true"></i> Remove from
                                                    Cart
                                                </button>
                                            </div>
                                            <div class="col-2"></div>
                                        </div>
                                    </form>
                                </c:when>
                                <%--Book not existed in cart--%>
                                <c:otherwise>
                                    <c:if test="${(not empty session) and (not empty user)}">
                                        <c:set var="bookBorrowStatus"
                                               value="${requestScope.MEMBER_BOOK_BORROW_STATUS}"/>
                                        <c:set var="memberTotalActiveBorrows"
                                               value="${sessionScope.MEMBER_TOTAL_ACTIVE_BORROWS}"/>
                                        <%--Book is available in stock--%>
                                        <c:if test="${bookObj.quantity gt 0}">
                                            <%--Book is in user's active borrowing list--%>
                                            <c:if test="${bookBorrowStatus eq 0}">
                                                <div class="row">
                                                    <div class="col-12 text-center my-2">
                                                        You are currently borrowing this book.
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:choose>
                                                <%--Borrowing limit reached--%>
                                                <c:when test="${(cart.cartQuantity ge 10)
                                                or (cart.cartQuantity + memberTotalActiveBorrows ge 10)
                                                or (memberTotalActiveBorrows ge 10)}">
                                                    <div class="row">
                                                        <div class="col-12 text-center my-1">
                                                            You have already reached the borrowing limit.
                                                        </div>
                                                    </div>
                                                </c:when>

                                                <%--Borrowing limit not reached--%>
                                                <c:otherwise>
                                                    <%--Reserved book is now available in stock--%>
                                                    <c:if test="${bookBorrowStatus eq 1}">
                                                        <div class="row">
                                                            <div class="col-12 text-center my-2">
                                                                You are currently reserving this book.
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <%--Add to Cart Button--%>
                                                    <c:if test="${bookBorrowStatus ne 0}">
                                                        <form action="AddBookToCartServlet" class="my-1">
                                                            <input type="hidden" name="memberPreviousAction"
                                                                   value="book_details:add_to_cart">
                                                            <div class="row">
                                                                <div class="col-2"></div>
                                                                <div class="col-8 text-center">
                                                                    <input type="hidden" name="bookPk"
                                                                           value="${bookObj.id}">
                                                                    <c:if test="${user.roleID eq '4'}">
                                                                        <button type="submit"
                                                                                class="btn btn-block"
                                                                                style="background-color: #748DDB; border-color: #748DDB; color: #fff"
                                                                                name="btAction" value="AddToCart">
                                                                            <i class="fa fa-cart-plus"
                                                                               aria-hidden="true"></i>
                                                                            Add to Cart
                                                                        </button>
                                                                    </c:if>
                                                                </div>
                                                                <div class="col-2"></div>
                                                            </div>
                                                        </form>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <%--Book is out of stock--%>
                                        <c:if test="${bookObj.quantity eq 0}">
                                            <c:choose>
                                                <%--Check if borrowed or reserved--%>
                                                <c:when test="${(bookBorrowStatus eq 1)
                                                        or (bookBorrowStatus eq 0)}">
                                                    <div class="row">
                                                        <div class="col-12 text-center my-2">
                                                            You are currently borrowing or reserving this book.
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <%--Allow to reserve--%>
                                                <c:otherwise>
                                                    <c:if test="${user.roleID eq '4'}">
                                                        <div class="row">
                                                            <div class="col-12 text-center my-2">
                                                                This book is currently out of stock.
                                                                <br/>
                                                                Add it to your cart to get notified when it becomes
                                                                available.
                                                            </div>
                                                        </div>
                                                        <form action="AddBookToCartServlet" class="my-1">
                                                            <input type="hidden" name="memberPreviousAction"
                                                                   value="book_details:add_to_cart">
                                                            <input type="hidden" name="bookPk"
                                                                   value="${bookObj.id}">
                                                            <div class="row">
                                                                <div class="col-2"></div>
                                                                <div class="col-8 text-center">
                                                                    <button type="submit"
                                                                            class="btn btn-success btn-block"
                                                                            name="btAction" value="AddToCart">
                                                                        <i class="fa fa-bookmark"
                                                                           aria-hidden="true"></i>
                                                                        Reserve Book
                                                                    </button>
                                                                </div>
                                                                <div class="col-2"></div>
                                                            </div>
                                                        </form>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-lg-3"></div>
                    </div>
                </div>
            </div>
            <%--End: Book Options Section--%>
            <%--Start: Book Comment Section--%>
            <%--List<CommentObj>--%>
            <c:set var="commentList" value="${requestScope.COMMENT_LIST}"/>
            <div class="card mt-3">
                <div class="card-body">
                    <%--Start: Other Comments--%>
                    <div class="card-title">Comments (${requestScope.COMMENT_AMOUNT})</div>
                    <c:if test="${requestScope.COMMENT_INVALID eq 'true'}">
                        <div class="alert alert-warning">Comment must be 0-255 characters.</div>
                    </c:if>
                    <c:if test="${not empty commentList}">
                        <c:forEach var="comment" items="${commentList}" varStatus="commentCounter">
                            <%--Start: Edit Comment Form--%>
                            <form action="EditCommentServlet" class="my-0 mx-0">
                                <div class="row mt-3">
                                    <div class="col-2">
                                        <img src="${comment.memberProfilePicturePath}"
                                             class="rounded-circle img-fluid"
                                             onerror="this.onerror=null; this.src='images/default-user-icon.png';"/>
                                    </div>
                                    <div class="col-8">
                                        <div class="card">
                                            <div class="card-body pt-1 pb-3">
                                                <div class="card-title my-1">
                                                    <span class="card-text mr-2">${comment.memberName}</span>
                                                    <small class="card-text font-weight-light" style="color: #748DDB">
                                                        <c:set var="memberRating"
                                                               value="${fn:substringBefore(comment.rating,'.')}"/>
                                                        <c:if test="${memberRating eq 0}">
                                                            <div class="badge badge-secondary text-wrap py-1">
                                                                No rating given
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${memberRating ne 0}">
                                                            <c:forEach begin="${1}" end="${memberRating}">
                                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                            </c:forEach>
                                                            <c:forEach begin="${memberRating}" end="${4}">
                                                                <i class="fa fa-star-o" aria-hidden="true"></i>
                                                            </c:forEach>
                                                        </c:if>
                                                    </small>
                                                </div>
                                                <div class="card-text" id="commentNo${commentCounter.count}">
                                                    <c:out value="${comment.textComment}"/>
                                                </div>
                                                    <%--Script to toggle edit box--%>
                                                <script>
                                                    $(function editToggle() {
                                                        $('#editSwitch${commentCounter.count}').bind('click', function () {
                                                            <%--Replace with editable comment box--%>
                                                            let $textarea = $('<textarea>')
                                                                .removeClass()
                                                                .addClass('form-control')
                                                                .attr('name', 'txtEditComment')
                                                                .attr('id', 'commentNo${commentCounter.count}')
                                                                .attr('rows', 3)
                                                                .text('${comment.textComment}');
                                                            $('#commentNo${commentCounter.count}')
                                                                .replaceWith($textarea);
                                                            <%--Replace with Cancel Edit Button--%>
                                                            let $cancelSwitch = $('<h6>')
                                                                .removeClass()
                                                                .addClass('preview-subject font-weight-normal mb-0')
                                                                .attr('style', 'cursor: default')
                                                                .attr('id', 'cancelEditSwitch${commentCounter.count}')
                                                                .text('Cancel Edit')
                                                            $('#editSwitch${commentCounter.count}')
                                                                .replaceWith($cancelSwitch)
                                                            <%--Show Edit Submit Button--%>
                                                            $('#editBtAction${commentCounter.count}').toggle();
                                                            <%--Cancel Edit Button Function--%>
                                                            $('#cancelEditSwitch${commentCounter.count}')
                                                                .bind('click', function () {
                                                                    <%--Replace with comment box--%>
                                                                    let $commentBox = $('<div>')
                                                                        .removeClass()
                                                                        .addClass('card-text')
                                                                        .attr('id', 'commentNo${commentCounter.count}')
                                                                        .text('${comment.textComment}');
                                                                    $('#commentNo${commentCounter.count}')
                                                                        .replaceWith($commentBox);
                                                                    <%--Replace with Edit Button--%>
                                                                    let $editSwitch = $('<h6>')
                                                                        .removeClass()
                                                                        .addClass('preview-subject font-weight-normal mb-0')
                                                                        .attr('style', 'cursor: default')
                                                                        .attr('id', 'editSwitch${commentCounter.count}')
                                                                        .text('Edit Comment');
                                                                    $('#cancelEditSwitch${commentCounter.count}')
                                                                        .replaceWith($editSwitch);
                                                                    <%--Hide Edit Submit Button--%>
                                                                    $('#editBtAction${commentCounter.count}').toggle();
                                                                    editToggle();
                                                                });
                                                        });
                                                    });
                                                </script>
                                                <small class="card-text">
                                                    <c:if test="${comment.edited}">
                                                        <div class="mt-2 font-weight-lighter text-right text-muted ">
                                                            Edited by ${comment.editorName}
                                                        </div>
                                                    </c:if>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-2" style="list-style-type: none;">
                                        <c:if test="${not empty session}">
                                            <c:if test="${not empty user}">
                                                <c:if test="${(user.roleID eq '1')
                                                or (user.roleID eq '2')
                                                or (user.roleID eq '3')
                                                or (user.id eq comment.memberID)}">
                                                    <li class="nav-item dropdown">
                                                        <a class="nav-link count-indicator" data-toggle="dropdown">
                                                            <i class="ti-more"></i>
                                                        </a>
                                                        <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list">
                                                            <a class="dropdown-item preview-item d-flex align-items-left">
                                                                <div class="preview-thumbnail">
                                                                    <i class="fa fa-edit"></i>
                                                                </div>
                                                                <div class="preview-item-content">
                                                                    <h6 id="editSwitch${commentCounter.count}"
                                                                        class="preview-subject font-weight-normal mb-0"
                                                                        style="cursor: default">
                                                                        Edit Comment
                                                                    </h6>
                                                                </div>
                                                            </a>
                                                            <a class="dropdown-item preview-item d-flex align-items-left">
                                                                <div class="preview-thumbnail">
                                                                    <i class="fa fa-trash-o"></i>
                                                                </div>
                                                                <div class="preview-item-content">
                                                                    <h6 id="deleteSwitch${commentCounter.count}"
                                                                        class="preview-subject font-weight-normal mb-0"
                                                                        data-toggle="modal"
                                                                        data-target="#deleteCmtModal${commentCounter.count}"
                                                                        style="cursor: default">
                                                                        Delete Comment
                                                                    </h6>
                                                                </div>
                                                            </a>
                                                        </div>
                                                    </li>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                            <%--Start: btAction to submit Edit--%>
                                        <input type="hidden" name="commentMemberID" value="${comment.memberID}"/>
                                        <input type="hidden" name="bookPk" value="${comment.bookID}"/>
                                        <button type="submit" class="btn btn-info btn-block"
                                                style="display: none"
                                                id="editBtAction${commentCounter.count}"
                                                name="btAction" value="EditComment">
                                            <i class="fa fa-pencil" aria-hidden="true"></i> Edit
                                        </button>
                                            <%--End: btAction to submit Edit--%>
                                    </div>
                                </div>
                            </form>
                            <%--End: Edit Comment Form--%>
                            <%--Start: Delete Comment Form--%>
                            <form action="DeleteCommentServlet" class="my-0">
                                <input type="hidden" name="commentMemberID" value="${comment.memberID}"/>
                                <input type="hidden" name="bookPk" value="${comment.bookID}"/>
                                <div class="modal fade" id="deleteCmtModal${commentCounter.count}"
                                     tabindex="-1" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="card-title">
                                                        Delete Comment
                                                    </div>
                                                    <div class="card-text">
                                                        This action cannot be undone. Are you sure?
                                                    </div>
                                                    <div class="modal-footer mt-3 pb-0">
                                                        <button type="button"
                                                                class="btn btn-secondary"
                                                                data-dismiss="modal">
                                                            Cancel
                                                        </button>
                                                        <button type="submit" class="btn btn-danger"
                                                                name="btAction"
                                                                value="DeleteComment">
                                                            Delete
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <%--End: Delete Comment Form--%>
                        </c:forEach>
                    </c:if>
                    <%--End: Other Comments--%>
                    <%--Start: Current User Comment--%>
                    <c:if test="${not empty session}">
                        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
                        <c:if test="${not empty user}">
                            <c:set var="hasCommented" value="${false}"/>
                            <c:forEach var="comment" items="${commentList}">
                                <c:if test="${comment.memberID eq user.id}">
                                    <c:set var="hasCommented" value="${true}"/>
                                </c:if>
                            </c:forEach>
                            <c:if test="${not hasCommented}">
                                <form action="AddCommentServlet" class="my-0 mx-0">
                                    <div class="row mt-3">
                                        <div class="col-2">
                                            <img src="${user.profilePicturePath}"
                                                 class="rounded-circle img-fluid"
                                                 onerror="this.onerror=null; this.src='images/default-user-icon.png';"/>
                                        </div>
                                        <div class="col-8">
                                            <div class="card">
                                                <div class="card-body pt-1 pb-0">
                                                    <div class="card-title my-1">
                                                    <span class="row mb-2">
                                                        <div class="card-text col-6">${user.name}</div>
                                                        <div class="input-group col-6">
                                                            <div class="input-group-prepend">
                                                                <label class="input-group-text py-0">
                                                                    Rating
                                                                </label>
                                                            </div>
                                                            <select name="bookRating"
                                                                    class="custom-select">
                                                                <option value="0">None</option>
                                                                <option value="1">1</option>
                                                                <option value="2">2</option>
                                                                <option value="3">3</option>
                                                                <option value="4">4</option>
                                                                <option value="5">5</option>
                                                            </select>
                                                        </div>
                                                    </span>
                                                    </div>
                                                    <div class="form-group">
                                                    <textarea class="form-control" rows="3"
                                                              name="txtComment"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-2">
                                            <input type="hidden" name="bookPk" value="${bookObj.id}"/>
                                            <button type="submit" class="btn btn-block"
                                                    name="btAction" value="AddComment"
                                            style="background-color: #748DDB; border-color: #748DDB; color: #fff">
                                                <i class="fa fa-paper-plane" aria-hidden="true"></i> Submit
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                        </c:if>
                    </c:if>
                    <%--End: Current User Comment--%>
                </div>
            </div>
            <%--End: Book Comment Section--%>
        </div>
        <%--End: Right Column--%>
        <%--Start: Empty Column--%>
        <div class="col-lg-2"></div>
        <%--End: Empty Column--%>
        <div class="col-12 my-4"></div>
    </div>
    <%--End: Book Section--%>
    <%--Start: Related Books Section--%>
    <%--Section Title--%>
    <%--    <div class="row mt-5">--%>
    <%--        <div class="col-lg-2"></div>--%>
    <%--        <div class="col-lg-8">--%>
    <%--            <div class="text-center h2">You might also like...</div>--%>
    <%--        </div>--%>
    <%--        <div class="col-lg-2"></div>--%>
    <%--    </div>--%>
    <%--Related Books--%>
    <%--    <div class="row mt-5 pb-5">--%>
    <%--        <div class="col-lg-2"></div>--%>
    <%--        <div class="col-lg-2">--%>
    <%--            <div class="card">--%>
    <%--                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"--%>
    <%--                     alt="software-engineering"/>--%>
    <%--                <div class="card-body">--%>
    <%--                    <h5 class="card-title text-center">--%>
    <%--                        Software Engineering 10th Edition--%>
    <%--                    </h5>--%>
    <%--                    <div class="text-center">--%>
    <%--                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More--%>
    <%--                            Info</a>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--        <div class="col-lg-2">--%>
    <%--            <div class="card">--%>
    <%--                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"--%>
    <%--                     alt="software-engineering"/>--%>
    <%--                <div class="card-body">--%>
    <%--                    <h5 class="card-title text-center">--%>
    <%--                        Software Engineering 10th Edition--%>
    <%--                    </h5>--%>
    <%--                    <div class="text-center">--%>
    <%--                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More--%>
    <%--                            Info</a>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--        <div class="col-lg-2">--%>
    <%--            <div class="card">--%>
    <%--                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"--%>
    <%--                     alt="software-engineering"/>--%>
    <%--                <div class="card-body">--%>
    <%--                    <h5 class="card-title text-center">--%>
    <%--                        Software Engineering 10th Edition--%>
    <%--                    </h5>--%>
    <%--                    <div class="text-center">--%>
    <%--                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More--%>
    <%--                            Info</a>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--        <div class="col-lg-2">--%>
    <%--            <div class="card">--%>
    <%--                <img class="card-img-top img-thumbnail img-fluid" src="images/software-engineering.jpg"--%>
    <%--                     alt="software-engineering"/>--%>
    <%--                <div class="card-body">--%>
    <%--                    <h5 class="card-title text-center">--%>
    <%--                        Software Engineering 10th Edition--%>
    <%--                    </h5>--%>
    <%--                    <div class="text-center">--%>
    <%--                        <a href="#" class="btn btn-info"><i class="fa fa-info-circle" aria-hidden="true"></i> More--%>
    <%--                            Info</a>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--        <div class="col-lg-2"></div>--%>
    <%--    </div>--%>
    <%--End: Related Books Section--%>
</div>
<%--Actual Body--%>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>