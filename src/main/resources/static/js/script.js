$(document).ready(function () {
    $("#flatsTable").DataTable();

    $(".dataTable").on("click mousedown", "tbody td", function () {
        openHref(this.textContent);
    });
});

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
