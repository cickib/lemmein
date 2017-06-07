var selectorify = function (id) {
    return "#" + id;
};

var slider = function (input, range, unit) {
    input = selectorify(input);
    range = selectorify(range);
    $(range).slider({
        range: true,
        min: 0,
        max: 300,
        values: [60, 120]
        ,
        slide: function (event, ui) {
            $(input).val(ui.values[0] +" "+ unit + " - " + ui.values[1] +" "+ unit);
        }
    });
    $(input).val($(range).slider("values", 0)  +" "+ unit + " - " + $(range).slider("values", 1)  +" "+ unit);
};

$(document).ready(function () {
    slider("rent", "rentRange", "ezer ft");
    slider("size", "sizeRange", "m2");

//
    $("#submitSearch").click(function () {
        var formId = "#"+$(this).parent().attribute("id");
        alert(formId)
        console.log(formId)

        $('#myForm :input');
        console.log($(this));
        console.log(this);
    });
});
