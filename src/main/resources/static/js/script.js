$(document).ready(function () {
    $('#flatsTable').DataTable();

    $('.dataTable').on('click', 'tbody td', function () {
        var url = this.textContent;
        var clickableHu = ".hu";
        var clickableCom = ".com";
        if (containsPrefix(url, clickableHu) || containsPrefix(url, clickableCom)) {
            window.location.href = fixUrl(url);
        }
    })
});

var containsPrefix = function (url, prefix) {
    return url.indexOf(prefix) >= 0;
};

var fixUrl = function (url) {
    var prefix = "http://www.";
    var prefixS = "https://www.";
    if (!(containsPrefix(url, prefix) || containsPrefix(url, prefixS))) {
        url = prefix + url;
    }
    return url;
};