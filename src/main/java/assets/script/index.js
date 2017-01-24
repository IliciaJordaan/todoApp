$(document).ready(function () {

    $(".removeItemBtn").on("click", function () {
        var itemId = $(this).val();
        $("#removeItemId").val(itemId);
        $('#frmRemoveTodoItem').submit();
    });

    $('.itemText').editable({
        mode: "inline",
        type:  'text',
        pk:    1,
        name:  'updateItemId',
        url:   'updateToDoItem',
        title: 'Update Value',
        toggle: 'manual',
        success: function() {
            location.href = "/";
        }
    });

    $(".editItemBtn").on("click", function (e) {
        e.stopPropagation();
        $(this).parent().find('.itemText').editable('toggle');
    });

});
