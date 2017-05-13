$(document).ready(function () {
    $('#flatsTable').DataTable();

    $('.dataTable').on('click', 'tbody td', function () {
        var url = this.textContent;
        if (isLink(url)) {
            window.location.href = url;
        }
    })
});

var isLink = function (url) {
    return containsPrefix(url, "://www.");
};

var containsPrefix = function (url, prefix) {
    return url.indexOf(prefix) >= 0
};
