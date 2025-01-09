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
    form = $('#filterForm');
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: form.serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    })
}

function resetFilter() {
    $("#startDate").val("");
    $("#endDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
    updateTable();
}