$(document).ready(function () {
    $("#flatsTable").DataTable();

    $(".dataTable").on("click mousedown", "tbody td", function () {
        openHref(this.textContent);
    });

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
                "district": getChecked("district"),
                "site": getChecked("site")
            }),
            contentType: "application/json; charset=utf-8"
        });
        scrollToMatches();
        getResults();
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


// checking, creating, opening valid links

var openHref = function (url) {
    if (isLink(url)) {
        window.open(url);
    }
};

var isLink = function (url) {
    return containsPrefix(url, "://www.");
};

var containsPrefix = function (url, prefix) {
    return url.indexOf(prefix) >= 0
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
        console.log(data["flats"])
        console.log(data["flats"].length)
        for (var i = 0; i < data["flats"].length; i++) {
            var flat = data["flats"][i];
            console.log("flat " + flat)
            $('#flatsTable > tbody:last-child').append(createFlatRecord(flat));
        }
    });
};

var createFlatRecord = function (flat) {
    console.log(flat.adUrl)
    return "<tr>" +
        "<td >" + flat.id + "</td>" +
        "<td>" + flat.squareMeter + "</td>" +
        "<td>" + flat.rent + "</td>" +
        "<td>" + flat.district + " | " + flat.address + "</td>" +
        "<td><a class='td-url'>" + flat.adUrl + "</a></td>" +
        "<td>" + flat.company + "</td></tr>";
};