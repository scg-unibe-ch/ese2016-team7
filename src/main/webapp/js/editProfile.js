$(document).ready(function() {

    $(".deleteCreditCard button").click(function (){
        var userId = $(this).attr("data-user-id");

        $.post("/profile/editProfile/deleteCreditCardFromUser", {userId: userId}, function() {
            var hasCreditCard = document.getElementById("hasCreditCard");
            hasCreditCard.checked = false;
            location.reload();
        });
    });
});

$(document).ready(function() {

    $(".addCreditCard button").click(function (){
        var userId = $(this).attr("data-user-id");

        $.post("/profile/editProfile/addCreditCardToUser", {userId: userId}, function() {
            var hasCreditCard = document.getElementById("hasCreditCard");
            hasCreditCard.checked = true;
            location.reload();
        });
    });
});