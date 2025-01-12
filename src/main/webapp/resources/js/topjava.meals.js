const mealsAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            paging: true,
            info: true,
            columns: [
                {"data": "dateTime"},
                {"data": "description"},
                {"data": "calories"},
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                0,
                "desc"
            ]
        })
    )
})

function getBetween() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $('#filterForm').serialize()
    }).done(function (data) {
        redrawTable(data)
    })
}

function resetFilter() {
    $("#filterForm")[0].reset();
    updateTable();
}