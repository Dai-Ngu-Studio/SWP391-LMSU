<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Author</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous" />
    <link rel="stylesheet" href="css/author.css" />

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="images/images/favicon.png" />
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>

<div class="box bg-light">
    <div class="top">
        <h2 class="pageTitle"><b>Author</b></h2>
    </div>
    <c:set var="authorObj" value="${requestScope.AUTHOR_OBJECT}"/>
    <div class="author">
        <div class="authorItem d-flex">
            <div class="d-flex flex-column">
                <img src="${pageContext.request.contextPath}/image/${authorObj.coverPath}"
                     class="rounded img-fluid img-thumbnail" alt="..."
                     onerror="this.onerror=null; this.src='images/NotAvailable.jpg';"
                />
                <form action="ShowAuthorBookServlet">
                    <input type="hidden" name="authorPk" value="${authorObj.authorID}"/>
                    <button type="submit" class="btn btn-light w-100 mt-3">See More Books of This Author</button>
                </form>
            </div>

            <div class="descriptionTxt px-4">
                <ul>
                    <li>
                        <p>
                            ${authorObj.authorName}
                        </p>
                    </li>
                    <li>
                        <p>
                            ${authorObj.authorBio}
                        </p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>