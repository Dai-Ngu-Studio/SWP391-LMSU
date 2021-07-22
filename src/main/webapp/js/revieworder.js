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
            loadCity(locationList);
        }
    });

    $.ajax({
        method: 'GET',
        url: 'LoadCityDataServlet',
        data: {
            boolLoadCity: 'false',
            boolLoadDistrict: 'true',
            boolLoadWard: 'false',
            txtCityID: $('#txtCity').val(),
            txtDistrictID: ''
        },
        dataType: 'json',
        success: function (locationList) {
            loadDistrict(locationList);
        }
    });

    $.ajax({
        method: 'GET',
        url: 'LoadCityDataServlet',
        data: {
            boolLoadCity: 'false',
            boolLoadDistrict: 'false',
            boolLoadWard: 'true',
            txtCityID: '',
            txtDistrictID: $('#txtDistrict').val()
        },
        dataType: 'json',
        success: function (locationList) {
            loadWard(locationList);
        }
    });

    function loadCity(cityList) {
        let cityID = $('#txtCity').val();
        let cityName = $(cityList['data']).filter(function () {
            return $(this).attr('ProvinceID').toString() === cityID
        })
            .attr('ProvinceName');
        $('#txtCity').val(cityName);
        $('#txtCityName').attr('value', cityName);
    }

    function loadDistrict(districtList) {
        let districtID = $('#txtDistrict').val();
        let districtName = $(districtList['data']).filter(function () {
            return $(this).attr('DistrictID').toString() === districtID
        })
            .attr('DistrictName');
        $('#txtDistrict').val(districtName);
        $('#txtDistrictName').attr('value', districtName);
    }

    function loadWard(wardList) {
        let wardID = $('#txtWard').val();
        let wardName = $(wardList['data']).filter(function () {
            return $(this).attr('WardCode').toString() === wardID
        })
            .attr('WardName');
        $('#txtWard').val(wardName);
        $('#txtWardName').attr('value', wardName);
    }
});
