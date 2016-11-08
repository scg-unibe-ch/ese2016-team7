$(document).ready(function() {

    $(".pictureThumbnail button").click(function () {
        var adId = $(this).attr("data-ad-id");
        var pictureId = $(this).attr("data-picture-id");

        $.post("/profile/editAd/deletePictureFromAd", {adId: adId, pictureId: pictureId}, function () {
            var button = $(".pictureThumbnail button[data-ad-id='" + adId + "'][data-picture-id='" + pictureId + "']");
            var div = $(button).parent();
            $(div).children().animate({opacity: 0}, 300, function () {
                $(div).remove();
            });
        });
    });
});

$(document).ready(function() {

    $(".visit button").click(function (){
        var adId = $(this).attr("data-visit-ad-id");
        var visitId = $(this).attr("data-visit-id");

        $.post("/profile/editAd/deleteVisitFromAd", {adId:adId, visitId:visitId}, function() {
            var button = $(".visit button[data-visit-ad-id='" + adId + "'][data-visit-id='" + visitId + "']");
            var div = $(button).parent();
            $(div).children().animate({opacity: 0}, 300, function () {
                $(div).remove();
            });
        });
    });
});