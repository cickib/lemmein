$(document).ready(function () {
    $("#flatsTable").DataTable();

    $(".dataTable").on("click mousedown", "tbody td", function () {
        openHref(this.textContent);
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
    });
});

var getChecked = function (name) {
    var checked = [];
    $("input[name='" + name + "']").each(function () {
        var id = this.id;
        if ($(createSelector(id)).prop("checked")) {
            checked.push(id)
        }
    });
    return checked;
};

var createSelector = function (id) {
    return "#" + id;
};

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
