const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
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
                [
                    0,
                    "asc"
                ]
            ],
            "rowCallback": function (row) {
                let checkbox = $(row).find("input[type='checkbox']");
                if (checkbox.length > 0 && !checkbox.is(":checked")) {
                    $(row).addClass('table table-danger');
                } else {
                    $(row).removeClass('table table-danger');
                }
            }
        })
    );
});

$(function () {
    $('#datatable').on('click', 'input[type="checkbox"]', function () {
        const userId = $(this).closest('tr').attr('id');
        const checkboxStatus = $(this).is(':checked');
        const row = $(this).closest('tr');
        const  checkbox = row.find('input[type="checkbox"]');
        $.ajax({
            url: ctx.ajaxUrl + userId,            type: "PATCH",
            contentType: "application/json",
            data: JSON.stringify({
                "id": userId,
                "enabled": checkboxStatus
            })
        }).done(function () {
            successNoty("User " + userId + " was been " + (!checkboxStatus ? "Deactivated" : "Activated"))
            !checkboxStatus ? row.addClass('table table-danger') : row.removeClass('table table-danger');
        }).fail(function () {
            checkbox.prop("checked", !checkboxStatus);
        })
    });
});