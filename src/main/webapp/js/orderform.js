// Validation for Direct Method
$(document).ready(function () {
    $('.directInput').on("change", function () {
        $('.directError').removeClass('text-danger').addClass('text-muted');
        $('.directInput').removeClass('is-invalid').addClass('is-valid');
        $('#errorPickupDate')
            .text("Your pick-up must be within 7 days from now.");
        $('#errorPickupTime')
            .text("Please choose a time slot to pick-up your books.");
        $.ajax({
            method: 'POST',
            url: 'CheckoutDirectValidationServlet',
            data: {
                txtPickupDate: $('#txtPickupDate').val(),
                txtPickupTime: $('#txtPickupTime').val()
            },
            datatype: 'json',
            success: function (responseJson) {
                $.each(responseJson, function (tagError, inputMessage) {
                    $('#' + tagError)
                        .removeClass('text-muted')
                        .addClass('text-danger');
                    console.log("inputMsg", inputMessage);
                    $.each(inputMessage, function (tagInput, errorMsg) {
                        $('#' + inputMessage['key'])
                            .removeClass('is-valid')
                            .addClass('is-invalid');
                        $('#' + tagError)
                            .text(errorMsg);
                    });
                });

                if ($.isEmptyObject(responseJson)) {
                    $('#btnDirectOrder')
                        .removeAttr('disabled')
                        .removeClass('btn-secondary')
                        .addClass('btn-primary');
                } else {
                    $('#btnDirectOrder')
                        .attr('disabled', '')
                        .removeClass('btn-primary')
                        .addClass('btn-secondary');
                }
            }
        });
    });
});

// Validation for Delivery Method
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

// Load city data
$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: 'LoadCityDataServlet',
        data: {
            boolLoadCity: 'true',
            boolLoadDistrict: 'false',
            boolLoadWard: 'false',
            txtCityID: '',
            txtDistrictID: ''
        },
        dataType: 'json',
        success: function (locationList) {
            loadCities(locationList);
        }
    });

    function loadCities(cityList) {
        $.each(cityList['data'], function () {
            $('<option>')
                .text($(this).attr('ProvinceName'))
                .val($(this).attr('ProvinceID'))
                .appendTo('#inputCity');
        });
    }

    function loadDistricts(districtList) {
        $.each(districtList['data'], function () {
            $('<option>')
                .text($(this).attr('DistrictName'))
                .val($(this).attr('DistrictID'))
                .appendTo('#inputDistrict');
        });
    }

    function loadWards(wardList) {
        $.each(wardList['data'], function () {
            $('<option>')
                .text($(this).attr('WardName'))
                .val($(this).attr('WardCode'))
                .appendTo('#inputWard');
        });
    }

    function clearDistricts() {
        $('#inputDistrict').empty();
        $('<option>')
            .attr('selected', '')
            .attr('disabled', '')
            .text('Select a district...')
            .appendTo('#inputDistrict');
    }

    function clearWards() {
        $('#inputWard').empty();
        $('<option>')
            .attr('selected', '')
            .attr('disabled', '')
            .text('Select a ward...')
            .appendTo('#inputWard');
    }

    $('#inputCity').on('change', function () {
        let selectedCity = $('#inputCity').val();
        $.ajax({
            method: 'GET',
            url: 'LoadCityDataServlet',
            data: {
                boolLoadCity: 'false',
                boolLoadDistrict: 'true',
                boolLoadWard: 'false',
                txtCityID: selectedCity,
                txtDistrictID: ''
            },
            dataType: 'json',
            success: function (locationList) {
                clearDistricts();
                loadDistricts(locationList);
                $("#inputDistrict option:selected").prop("selected", false);
            }
        });
    });

    let inputDistrict = $('#inputDistrict');
    const observerConfig = {childList: true};

    const renderWards = function (mutationsList, observer) {
        let selectedDistrict = $('#inputDistrict').val();
        $.ajax({
            method: 'GET',
            url: 'LoadCityDataServlet',
            data: {
                boolLoadCity: 'false',
                boolLoadDistrict: 'false',
                boolLoadWard: 'true',
                txtCityID: '',
                txtDistrictID: selectedDistrict
            },
            dataType: 'json',
            success: function (locationList) {
                clearWards();
                loadWards(locationList);
                $("#inputWard option:selected").prop("selected", false);
            }
        });
    }

    $('#inputDistrict').on('change', function () {
        let selectedDistrict = $('#inputDistrict').val();
        $.ajax({
            method: 'GET',
            url: 'LoadCityDataServlet',
            data: {
                boolLoadCity: 'false',
                boolLoadDistrict: 'false',
                boolLoadWard: 'true',
                txtCityID: '',
                txtDistrictID: selectedDistrict
            },
            dataType: 'json',
            success: function (locationList) {
                clearWards();
                loadWards(locationList);
                $("#inputWard option:selected").prop("selected", false);
            }
        });
    });

    const districtObs = new MutationObserver(renderWards);
    const startObserver = function () {
        inputDistrict.each(function () {
            districtObs.observe(this, observerConfig);
        });
    };
    const stopObserver = function () {
        districtObs.disconnect();
    };
    startObserver();
});
