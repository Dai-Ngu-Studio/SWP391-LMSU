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
            }
        });
    });

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
            }
        });
    });
});
