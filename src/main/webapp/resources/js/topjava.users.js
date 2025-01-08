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
            ]
        })
    );
});

$(function () {
    $('#datatable').on('click', 'input[type="checkbox"]', function () {
        const userId = $(this).closest('tr').attr('id');
        let checkboxStatus = $(this).is(':checked');

        if (checkboxStatus === false) {
            toggleUserStatus(userId, checkboxStatus, "Deactivated");
            $(this).closest('tr').addClass('table table-danger');
        } else {
            toggleUserStatus(userId, checkboxStatus, "Activated");
            $(this).closest('tr').removeClass('table table-danger')
        }
    });
});

function toggleUserStatus(userId, checkboxStatus, profileStatus) {
    $.ajax({
        url: ctx.ajaxUrl + userId,
        type: "PATCH",
        contentType: "application/json",
        data: JSON.stringify({
            "id": userId,
            "enabled": checkboxStatus
        })
    }).done(function () {
        successNoty("User " + userId + " was been " + profileStatus)
    }).fail(function () {
        failNoty("some thing wrong...");
    })
}