<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Announcement</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%--Main css--%>
    <link rel="stylesheet" href="css/announcement.css">
    <%--Bootstrap CDN--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <%--FAVICON--%>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<c:set value="${requestScope.ANNOUNCEMENT_LIST}" var="list"/>
<c:if test="${empty list}">
    <div class="row my-4">
        <div class="col-4"></div>
        <div class="col-4">
            <div class="card mt-2">
                <div class="card-body text-center">
                    <div class="card-text">
                        No announcement have been made yet.
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4"></div>
    </div>
</c:if>
<c:if test="${not empty list}">
    <div class="d-flex flex-column align-items-center p-10 bg-light">
        <div class="announcement">
            <c:forEach var="announcement" items="${list}">
                ${announcement.announcementText}
            </c:forEach>
                <%--<div class="d-flex flex-column align-items-center mb-5">
                    <h6><b>INFORMATION AND LIBRARY CENTER</b></h6>
                    <h3><b>ANNOUNCEMENT</b></h3>
                </div>
                <div>
                    <p>Dear Library Users,</p>
                    <p>
                        Delivering textbook schedule for Summer 2021 semester at FPTU - Hoa Lac Library. Library users are
                        required to follow below schedule:
                    </p>
                    <p class="text-danger">
                        <b>
                            Start: Tuesday 04/5/2021 to Friday 21/5/2021 at room no 108.
                        </b>
                    </p>
                    <p>
                        Textbook for subjects: ENT203, TRS401, TRS501, TRS601, JIT301 and Japanese subjects (JPD116, JPD126,
                        JPD216, JPD226, JPD316, JPD326) <b class="text-danger">from Monday 10/5/2021 to 21/5/2021at room no
                        107</b> .
                    </p>
                    <h4><b>Time:</b></h4>
                    <p>
                        - Morning: From 08:30 to 11:30 <br> - Afternoon: From 13:30 to 16:30
                    </p>
                    <h4><b>Notes:</b></h4>
                    <p>
                        - Students must return old textbooks before borrow new textbooks; <br> - Students who don't borrow and
                        get textbooks as schedule must take responsibility of having no textbooks; <br> - Students can view
                        infomartion of textbooks at
                        here
                    </p>
                    <h4><b>Should you have any inquiry, please contact us via:</b></h4>
                    <p>
                        <b>Phone No:</b> 024-6680 5912 <br>
                        <b>Email:</b> lmsu@gmail.com <br> <b>Fanpage:</b> https://www.facebook.com/lmsu
                    </p>
                </div>--%>
        </div>
    </div>
</c:if>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
