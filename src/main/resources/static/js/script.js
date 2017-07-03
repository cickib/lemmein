$(document).ready(function () {

    $("#dAll").click(function () {
        checkAll(this);
    });

    $("#sAll").click(function () {
        checkAll(this);
    });

    $("#searchBtn").click(function () {

        var rentFrom = $("#rentFrom").val();
        var rentTo = $("#rentTo").val();
        var sizeFrom = $("#sizeFrom").val();
        var sizeTo = $("#sizeTo").val();
        $.ajax({
            method: "POST",
            url: "/search",
            dataType: "json",
            data: JSON.stringify({
                "rentFrom": rentFrom,
                "rentTo": rentTo,
                "sizeFrom": sizeFrom,
                "sizeTo": sizeTo,
                "districts": getChecked("district"),
                "sites": getChecked("site")
            }),
            contentType: "application/json; charset=utf-8"
        });
        getResults();
        scrollToMatches();
    });
});


// collect checked checkboxes to array

var getChecked = function (name) {
    var checked = [];
    $("input[name='" + name + "']").each(function () {
        var id = this.id;
        if ($(selectorify(id)).prop("checked")) {
            checked.push(id)
        }
    });
    return checked;
};


// create jquery selector from id value

var selectorify = function (id) {
    return "#" + id;
};


// checkbox checking/unchecking

var checkAll = function (target) {
    var bool = $(selectorify(target.id)).prop("checked");
    $("input[name='" + target.name + "']").each(function () {
        var id = this.id;
        checkCheckBox(id, bool)
    });
};

var checkCheckBox = function (id, boolValue) {
    $(selectorify(id)).prop("checked", boolValue);
};


// autoscroll to results

var scrollToMatches = function () {
    $("html, body").animate({
        scrollTop: $("#flatsTable").offset().top
    }, 2000);
};


// collect flats from backend, insert data as new row

var getResults = function () {
    $.getJSON("/results", function (data) {
        $('#flatsTable').DataTable({
            data: data["flats"],
            "columns": [
                { "data": "id" },
                { "data": "squareMeter" },
                { "data": "rent" },
                { "data": "district"
                },
                { "data": "address" },
                { "data": "adUrl" ,
                    "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                        $(nTd).html("<a class='td-url' href='"+oData.adUrl+"'>Checkout!</a>");
                    }},
                { "data": "company" },
            ]
        });
    });
};
