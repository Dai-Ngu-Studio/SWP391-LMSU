<%--
  Created by IntelliJ IDEA.
  User: NDungx
  Date: 6/3/2021
  Time: 8:05 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Policy</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <%--Bootstrap CDN--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <%--jquery CDN--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <%--Fontawsome CDN--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <%--Main css--%>
    <link rel="stylesheet" href="css/policy.css"/>
    <%--FAVICON--%>
    <link rel="shortcut icon" href="images/images/favicon.png"/>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<div class="box">
    <div class="policy">
        <div class="top">
            <h2 class="mb-5"><b>Policy</b></h2>
        </div>
        <div class="introduction">
            <p>
                - PRESENT YOUR VALID CARD (STUDENT/STAFF CARD) TO BORROW OR RETURN BOOKS
            </p>
        </div>
        <div class="descriptionTitle">
            <p>
                <b>a - REFERENCE BOOKS</b>
            </p>
        </div>
        <div class="descriptionTxt">
            <p>
                - Users can borrow up to 10 items (Vietnamese: 7 days; Foreign Language: 14 days) and you can renew up
                to 4 times.
            </p>
        </div>
        <div class="descriptionTitle">
            <p>
                <b>b - TEXTBOOKS</b>
            </p>
        </div>

        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Student: Collect textbooks at the beginning of block and return by the end of block before
                        borrow textbook of new block.
                    </p>
                </li>
                <li>
                    <p>
                        Lecturer: Collect textbooks at the beginning semester and return before the next semester/or
                        complete teaching.
                    </p>
                </li>
                <li>
                    <p>
                        Textbooks can only be renewed up to 1 week when you having suitable reasons.
                    </p>
                </li>
            </ul>
        </div>

        <div class="descriptionTitle">
            <p>
                <b>c - NOTE FOR RENTAL TEXTBOOK COLLECTION</b>
            </p>
        </div>

        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Make sure book condition notes at the end of your borrowed books are updated. You are
                        responsible for the condition of everything while it is on loan to you. Please take good care of
                        books or you pay for the damage according to the regulation;
                    </p>
                </li>
                <li>
                    <p>
                        Books and references can only be renewed if not overdue;
                    </p>
                </li>
                <li>
                    <p>
                        To renew books, you can contact us by making phone call, sending email or in person.
                    </p>
                </li>
                <li>
                    <p>
                        If you find any damage when you first receive your book, notify the ILC staff to exchange the
                        book during the first week of semester. Any damage is found by the end of semester will be
                        fined.
                    </p>
                </li>
            </ul>
        </div>

        <div class="descriptionTitle">
            <p>
                <b>d - FINES & PENALTY</b>
            </p>
        </div>

        <div class="descriptionTxt">
            <ul>
                <li>
                    <p>
                        Overdue books will befined 5,000 VND/item/day
                    </p>
                </li>
                <li>
                    <p>
                        Damaged items which can be used will occur an amount depend on how serious of damage
                    </p>
                </li>
                <li>
                    <p>
                        For broken items/overdue 30 days or lost items will be charged as:
                    </p>
                </li>
                <li>
                    <p>
                        ReplacementFine = Book Cover Price (double price for reference books) + Overdue Fine.
                    </p>
                </li>
                <li>
                    <p>
                        Note: If the overdue fine is higher than the book price, the maximum fine will be the cost of
                        the book.
                    </p>
                </li>
                <li>
                    <p>
                        E.g. An, Nguyen Van borrowed SUMMIT 1 textbook which costs 128,000 and was overdue 60 days
                        (Overdue Fine = 300,000 > book price).
                    </p>
                </li>
                <li>
                    <p>
                        There placement fine for An, Nguyen Van: 128,000 + 128,000 = 256,000 VND
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="footer.html"></jsp:include>
</body>
</html>
