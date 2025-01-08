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
    let startDate = $("#startDate").val();
    let endDate = $("#endDate").val();
    let startTime = $("#startTime").val();
    let endTime = $("#endTime").val();

    let filterParams = "filter?startDate=" + startDate + "&endDate=" + endDate
        + "&startTime=" + startTime + "&endTime=" + endTime

    $.get(ctx.ajaxUrl + filterParams, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}
function resetFilter() {
    $("#startDate").val("");
    $("#endDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
}