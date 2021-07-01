<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Checkout - LMSU</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</head>

<body>
<jsp:directive.page pageEncoding="UTF-8"/>
<jsp:directive.page contentType="text/html; charset=UTF-8"/>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.html"></jsp:include>
<!--Actual Body-->
<div class="bg-light py-4">

    <c:set var="session" value="${sessionScope}"/>
    <c:if test="${not empty session}">
        <div class="row mt-5">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="tab-content" id="nav-tabContent" style="border: none">
                                <%--Start: Delivery Order Form--%>
                            <script>
                                $(document).ready(function () {
                                    $('.deliverInput').on("input", function () {
                                        $('.deliverError').removeClass('text-danger').addClass('text-muted');
                                        $('.deliverInput').removeClass('is-invalid').addClass('is-valid');
                                        $.ajax({
                                            method: 'POST',
                                            url: 'CheckoutDeliveryValidationServlet',
                                            data: {
                                                txtReceiverName: $('#inputReceiverName').val(),
                                                txtPhoneNumber: $('#inputPhoneNumber').val(),
                                                txtAddressOne: $('#inputAddressOne').val(),
                                                txtAddressTwo: $('#inputAddressTwo').val(),
                                                txtCity: $('#inputCity').val(),
                                                txtDistrict: $('#inputDistrict').val(),
                                                txtWard: $('#inputWard').val()
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
                                                    $('#btnDeliveryOrder')
                                                        .removeAttr('disabled')
                                                        .removeClass('btn-secondary')
                                                        .addClass('btn-primary');
                                                } else {
                                                    $('#btnDeliveryOrder')
                                                        .attr('disabled', '')
                                                        .removeClass('btn-primary')
                                                        .addClass('btn-secondary');
                                                }
                                            }
                                        });
                                    });
                                });
                            </script>
                            <form action="ReviewOrderServlet" method="POST" class="my-0">
                                <div class="form-group my-10">
                                    <label for="inputReceiverName">Receiver Name</label>
                                    <input type="text" class="form-control deliverInput" id="inputReceiverName"
                                           name="txtReceiverName" placeholder="Sinh Vi En"
                                           value="${sessionScope.CHECKOUT_RECEIVERNAME}"
                                    />
                                    <small id="errorReceiverName"
                                           class="form-text text-muted deliverError">
                                        Name of receiver must be 2-60 characters long, and must only contain
                                        letters.
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="inputPhoneNumber">Phone Number</label>
                                    <input type="text" class="form-control deliverInput" id="inputPhoneNumber"
                                           name="txtPhoneNumber" placeholder="090000000000"
                                           value="${sessionScope.CHECKOUT_PHONENUMBER}"
                                    />
                                    <small id="errorPhoneNumber"
                                           class="form-text text-muted deliverError">
                                        Phone number must be 10 characters long, and must only contain
                                        numbers.
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="inputAddressOne">Street Address</label>
                                    <input type="text" class="form-control deliverInput" id="inputAddressOne"
                                           name="txtAddressOne" placeholder="1234 D1 Street"
                                           value="${sessionScope.CHECKOUT_ADDRESSONE}"
                                    />
                                    <small id="errorAddressOne"
                                           class="form-text text-muted deliverError">
                                        Street address must be 5-50 characters long, can contain letters,
                                        numbers and special characters.
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="inputAddressTwo">Residence Address</label>
                                    <input type="text" class="form-control deliverInput" id="inputAddressTwo"
                                           name="txtAddressTwo" placeholder="Apartment, or floor"
                                           value="${sessionScope.CHECKOUT_ADDRESSTWO}"
                                    />
                                    <small id="errorAddressTwo"
                                           class="form-text text-muted deliverError">
                                        Residence address is optional, must be 0-50 characters long, can contain
                                        letters, numbers and special characters.
                                    </small>
                                </div>
                                <script>
                                    $(document).ready(function () {
                                        var cityData;
                                        $.ajax({
                                            method: 'POST',
                                            url: 'data/city-data.json',
                                            dataType: 'json',
                                            success: function (responseJson) {
                                                cityData = responseJson;
                                                loadCities();
                                                console.log(cityData);
                                            }
                                        });

                                        function loadCities() {
                                            $.each(cityData, function (key, value) {
                                                $('<option>')
                                                    .text(value['Name'])
                                                    .val(value['Name'])
                                                    .appendTo('#inputCity');
                                            });
                                        }

                                        var selectedCity;
                                        var districts;
                                        var selectedDistrict;
                                        var wards;

                                        $('#inputCity').on('change', function () {
                                            $('#inputDistrict').empty();
                                            $('<option>')
                                                .attr('selected', '')
                                                .attr('disabled', '')
                                                .text('Select a district...')
                                                .appendTo('#inputDistrict');
                                            selectedCity = cityData
                                                .filter(n => n.Name === $('#inputCity').val());
                                            districts = selectedCity[0]['Districts'];
                                            console.log(districts);
                                            $.each(districts, function (key, value) {
                                                $('<option>')
                                                    .text(value['Name'])
                                                    .val(value['Name'])
                                                    .appendTo('#inputDistrict');
                                            });
                                        });

                                        $('#inputDistrict').on('change', function () {
                                            $('#inputWard').empty();
                                            $('<option>')
                                                .attr('selected', '')
                                                .attr('disabled', '')
                                                .text('Select a ward...')
                                                .appendTo('#inputWard');
                                            selectedDistrict = districts
                                                .filter(n => n.Name === $('#inputDistrict').val());
                                            wards = selectedDistrict[0]['Wards'];
                                            console.log(wards);
                                            $.each(wards, function (key, value) {
                                                $('<option>')
                                                    .text(value['Name'])
                                                    .val(value['Name'])
                                                    .appendTo('#inputWard');
                                            });
                                        });
                                    });
                                </script>
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label for="inputCity">City</label>
                                        <select id="inputCity" name="txtCity"
                                                class="custom-select deliverInput">
                                            <option selected disabled>Select a city..</option>
                                        </select>
                                        <small id="errorCity"
                                               class="form-text text-muted deliverError">
                                            Please select a city.
                                        </small>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="inputDistrict">District</label>
                                        <select id="inputDistrict" name="txtDistrict"
                                                class="custom-select deliverInput">
                                            <option selected disabled>Select a city first...</option>
                                        </select>
                                        <small id="errorDistrict"
                                               class="form-text text-muted deliverError">
                                            Please select a district.
                                        </small>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <label for="inputWard">Ward</label>
                                        <select id="inputWard" name="txtWard"
                                                class="custom-select deliverInput">
                                            <option selected disabled>Select a district first...</option>
                                        </select>
                                        <small id="errorWard"
                                               class="form-text text-muted deliverError">
                                            Please select a ward.
                                        </small>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary"
                                            id="btnDeliveryOrder"
                                            name="btAction" value="ReturnOrder"
                                            disabled>
                                        Checkout
                                    </button>
                                </div>
                            </form>
                                <%--End: Delivery Order Form--%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </c:if>
</div>
<!--Actual Body-->
<jsp:include page="scrolltotop.html"></jsp:include>
<jsp:include page="footer.html"></jsp:include>
</body>

</html>