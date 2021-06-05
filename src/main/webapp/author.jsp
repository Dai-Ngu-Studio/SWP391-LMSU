<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<script>
    $(function() {
        $("#includedHeader").load("header.html");
    });
</script>
<script>
    $(function() {
        $("#includedNavbar").load("navbar.html");
    });
</script>
<script>
    $(function() {
        $("#includedScrollToTop").load("scrolltotop.html");
    });
</script>
<script>
    $(function() {
        $("#includedFooter").load("footer.html");
    });
</script>
<div id="includedHeader"></div>
<div id="includedNavbar"></div>
<div id="includedScrollToTop"></div>

<div class="box bg-light">
    <div class="top">
        <h2 class="pageTitle"><b>Author</b></h2>
    </div>

    <c:set var="authorList" value="${requestScope.AUTHOR_LIST}"/>
    <c:forEach var="author" items="${authorList}">
    <div class="author">


            <div class="authorItem d-flex">
                <div class="d-flex flex-column">
                    <div class="authorIMG w-100"></div>
                    <button class="btn btn-light w-100 mt-3">See More Books of This Author</button>
                </div>

                <div class="descriptionTxt px-4">
                    <ul>
                        <li>
                            <p>
                                ${author.authorName}
                            </p>
                        </li>
                        <li>
                            <p>
                                ${author.authorBio}
                            </p>
                        </li>
                        <li>

                        </li>
                    </ul>
                </div>
            </div>
        </c:forEach>



        <!--<div class="authorItem d-flex">
            <div class="d-flex flex-column">
                <div class="authorIMG w-100"></div>
                <button class="btn btn-light w-100 mt-3">See More Books of This Author</button>
            </div>
            <div class="descriptionTxt px-4">
                <ul>
                    <li>
                        <p>
                            Karl E. Wiegers (born 1953) is an American software engineer, consultant, and trainer in the areas of software development, management, and process improvement. Previously, he spent 18 years at Eastman Kodak Company as a photographic research scientist,
                            software developer, software manager, and software process and quality improvement leader.
                        </p>
                    </li>
                    <li>
                        <p>
                            When he's not on the computer, Karl enjoys wine tasting, playing guitar, writing and recording songs, and doing volunteer work
                        </p>
                    </li>
                    <li>
                        <p>
                            Karl is the author of numerous books and article on software development, chemistry, self-help, and military history. His books include the two previous editions of <span class="bookTitle">Software Requirements</span> (Microsoft
                            Press, 1999 and 2003),
                            <span class="bookTitle">More About Software Requirements</span> (Microsoft Press, 2006),
                            <span class="bookTitle">Pratical Project Initiation</span> (Microsoft Press, 2007),
                            <span class="bookTitle">Peer Reviews in Software</span> (Addison-Wesley, 2002), and
                            <span class="bookTitle">Creating a Software Engineering Culture</span> (Dorest House Publishing, 1996). He is also the author of a <span class="bookTitle">memoir of life
                                    lessons, Pearl from Sand</span> (Morgan James Publishing, 1996).
                        </p>
                    </li>
                </ul>
            </div>
        </div> --->

        <!--<div class="authorItem d-flex">
            <div class="d-flex flex-column">
                <div class="authorIMG w-100"></div>
                <button class="btn btn-light w-100 mt-3">See More Books of This Author</button>
            </div>
            <div class="descriptionTxt px-4">
                <ul>
                    <li>
                        <p>
                            Rex Black (born July 16, 1964) is a software engineer, entrepreneur and an author in the field of software testing. Black graduated from the University of California at Los Angeles (UCLA) in 1990 with a bachelors of science in computer science and engineering.
                            In 1983, Black started work in the software engineering field and has spent more than 20 years in software testing.
                        </p>
                    </li>
                    <li>
                        <p>
                            Black is chief executive officer and founder of RBCS, Inc. Black has also taught courses in managing the testing process at the University of Texas at Austin Center for Lifelong Engineering Education and is an adjunct faculty member and course developer
                            of business analysis and software testing curricula for Villanova University. He serves on editorial board of Software Test & Performance Magazine.
                        </p>
                    </li>
                </ul>
            </div>
        </div> --->

        <!--<div class="authorItem d-flex">
            <div class="d-flex flex-column">
                <div class="authorIMG w-100"></div>
                <button class="btn btn-light w-100 mt-3">See More Books of This Author</button>
            </div>
            <div class="descriptionTxt px-4">
                <ul>
                    <li>
                        <p>
                            Ian Sommervile (February 23, 1951) was born in Glasgow, Scotland and educated at Strathclyde University (Physics) and St Andrews University (Computer Science). He joined the School of Computer Science at St Andrews University, Scotland in April 2006 after
                            almost 20 years (1986-2006) as a professor of software engineering in the Computing Department at Lancaster University in northern England. Previously, he was a lecturer in Computer Science at Heriot-Watt University, Edinburgh
                            (1975-78) and at Strathclyde University, Glasgow (1978-86).
                        </p>
                    </li>
                    <li>
                        <p>
                            On his personal web site and blog, he maintain information about my interests outside of work - hill walking and backpacking, photography, food and reading. he also maintain a blog of restaurant reviews, mostly but not exclusively in Scotland. </p>
                    </li>
                </ul>
            </div>
        </div> --->
        <div class="d-flex justify-content-center">
            <div class="dataTables_paginate paging_simple_numbers" id="order-listing_paginate">
                <ul class="pagination">
                    <li class="paginate_button page-item previous disabled" id="order-listing_previous"><a href="#" aria-controls="order-listing" data-dt-idx="0" tabindex="0" class="page-link">Previous</a></li>
                    <li class="paginate_button page-item active"><a href="#" aria-controls="order-listing" data-dt-idx="1" tabindex="0" class="page-link">1</a></li>
                    <li class="paginate_button page-item "><a href="#" aria-controls="order-listing" data-dt-idx="2" tabindex="0" class="page-link">2</a></li>
                    <li class="paginate_button page-item next" id="order-listing_next"><a href="#" aria-controls="order-listing" data-dt-idx="3" tabindex="0" class="page-link">Next</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="includedFooter"></div>
</body>

</html>
