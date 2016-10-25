<%@page import="ch.unibe.ese.team1.model.Ad" %>
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile"/>

<c:import url="template/header.jsp"/>

<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   Ad Description</pre>

<script src="/js/image_slider.js"></script>
<script src="/js/adDescription.js"></script>

<script>
    var shownAdvertisementID = "${shownAd.id}";
    var shownAdvertisement = "${shownAd}";

    function attachBookmarkClickHandler() {
        $("#bookmarkButton").click(function () {

            $.post("/bookmark", {id: shownAdvertisementID, screening: false, bookmarked: false}, function (data) {
                $('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
                switch (data) {
                    case 0:
                        alert("You must be logged in to bookmark ads.");
                        break;
                    case 1:
                        // Something went wrong with the principal object
                        alert("Return value 1. Please contact the WebAdmin.");
                        break;
                    case 3:
                        $('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
                        break;
                    default:
                        alert("Default error. Please contact the WebAdmin.");
                }

                attachBookmarkedClickHandler();
            });
        });
    }

    function attachBookmarkedClickHandler() {
        $("#bookmarkedButton").click(function () {
            $.post("/bookmark", {id: shownAdvertisementID, screening: false, bookmarked: true}, function (data) {
                $('#bookmarkedButton').replaceWith($('<a class="right" id="bookmarkButton">' + "Bookmark Ad" + '</a>'));
                switch (data) {
                    case 0:
                        alert("You must be logged in to bookmark ads.");
                        break;
                    case 1:
                        // Something went wrong with the principal object
                        alert("Return value 1. Please contact the WebAdmin.");
                        break;
                    case 2:
                        $('#bookmarkedButton').replaceWith($('<a class="right" id="bookmarkButton">' + "Bookmark Ad" + '</a>'));
                        break;
                    default:
                        alert("Default error. Please contact the WebAdmin.");

                }
                attachBookmarkClickHandler();
            });
        });
    }

    $(document).ready(function () {
        attachBookmarkClickHandler();
        attachBookmarkedClickHandler();
        showTimeLeft();

        $.post("/bookmark", {id: shownAdvertisementID, screening: true, bookmarked: true}, function (data) {
            if (data == 3) {
                $('#bookmarkButton').replaceWith($('<a class="right" id="bookmarkedButton">' + "Bookmarked" + '</a>'));
                attachBookmarkedClickHandler();
            }
            if (data == 4) {
                $('#shownAdTitle').replaceWith($('<h1>' + "${shownAd.title}" + '</h1>'));
            }
        });

        $("#newMsg").click(function () {
            $("#content").children().animate({opacity: 0.4}, 300, function () {
                $("#msgDiv").css("display", "block");
                $("#msgDiv").css("opacity", "1");
            });
        });

        $("#messageCancel").click(function () {
            $("#msgDiv").css("display", "none");
            $("#msgDiv").css("opacity", "0");
            $("#content").children().animate({opacity: 1}, 300);
        });

        $("#messageSend").click(function () {
            if ($("#msgSubject").val() != "" && $("#msgTextarea").val() != "") {
                var subject = $("#msgSubject").val();
                var text = $("#msgTextarea").val();
                var recipientEmail = "${shownAd.user.username}";
                $.post("profile/messages/sendMessage", {
                    subject: subject,
                    text: text,
                    recipientEmail: recipientEmail
                }, function () {
                    $("#msgDiv").css("display", "none");
                    $("#msgDiv").css("opacity", "0");
                    $("#msgSubject").val("");
                    $("#msgTextarea").val("");
                    $("#content").children().animate({opacity: 1}, 300);
                })
            }
        });

        $("#makeBid").click(function () {
            if ($("#bidAmount").val() != "") {
                var amount = $("#bidAmount").val();
                var id = ${shownAd.id};
                var currentPrice = ${shownAd.price};


                if (amount > currentPrice) {
                    $.post("ad/makeBid", {amount: amount, id: id}, function () {
                        // alert("You bid: " + amount + " CHF");
                        $("#bidAmount").val("");
                        location.reload();
                    })
                } else {
                    alert("You have to bid higher than the current price.")
                }
            }
        });
    });

</script>


<!-- format the dates -->
<fmt:formatDate value="${shownAd.moveInDate}" var="formattedMoveInDate"
                type="date" pattern="dd.MM.yyyy"/>
<fmt:formatDate value="${shownAd.creationDate}" var="formattedCreationDate"
                type="date" pattern="dd.MM.yyyy"/>


<h1 id="shownAdTitle">${shownAd.title}
    <c:choose>
        <c:when test="${loggedIn}">
            <a class="right" id="bookmarkButton">Bookmark Ad</a>
        </c:when>
    </c:choose>
</h1>


<hr/>

<section>
    <c:choose>
        <c:when test="${loggedIn}">
            <c:if test="${loggedInUserEmail == shownAd.user.username }">
                <a href="<c:url value='/profile/editAd?id=${shownAd.id}' />">
                    <button type="button">Edit Ad</button>
                </a>
            </c:if>
        </c:when>
    </c:choose>
    <br>
    <br>


    <script>
        function showAllBids() {
            if ($("#bids").css("display") == "none") {
                $("#bids").css("display", "");
                $("#showBids").html("Hide All Bids");
            } else {
                $("#bids").css("display", "none");
                $("#showBids").html("Show All Bids");
            }
        }
    </script>

    <script>
        function showTimeLeft(){
            var expired = new Date('<fmt:formatDate value="${shownAd.expireDate}" pattern="yyyy-MM-dd HH:mm:ss"/>');
            var current = new Date();
            if(current > expired){
                $('#bidInfo').html("<h2>We are sorry but this acution is over!</h2>");
            }else{
                var diff = expired - current;
                var msec = diff;
                var dd = Math.floor(msec / 1000 / 60 / 60 / 24);
                var hh = Math.floor(msec / 1000 / 60 / 60);
                msec -= hh * 1000 * 60 * 60;
                var mm = Math.floor(msec / 1000 / 60);
                msec -= mm * 1000 * 60;
                var ss = Math.floor(msec / 1000);
                msec -= ss * 1000;
                $('#timeLeft').html("Time Left: "+ dd +"T " +hh+"h "+mm+"m "+ ss+"s");
            }
        }

        var timer = setInterval(showTimeLeft, 1000);
    </script>

    <div id="bidList" class="adDescDiv">
        <div id="bidInfo">
        <h2 id="timeLeft">Expire Date: <fmt:formatDate value="${shownAd.expireDate}" pattern="dd.MM.yyyy HH:mm:ss"/></h2>

        <h2>Current Price: ${shownAd.price} CHF </h2>

        <c:choose>
            <c:when test="${loggedIn}">
                <form>
                    <input class="bidInput" type="number" id="bidAmount" placeholder="Amount" style='width:300px'/>
                    <br>
                    <button type="button" id="makeBid" class="bidButton">Make Bid</button>
                </form>
            </c:when>
            <c:otherwise>
                Login to make bids.
            </c:otherwise>
        </c:choose>
        </div>
        <br>
        <a href="javascript:void(0);" id="showBids" onclick="showAllBids();" style="color: #ff00ff">Show All
            Bids</a><br>


        <table id="bids" style='display:none'>
            <c:forEach items="${bids }" var="bid">
                <tr>
                    <td>
                        <fmt:formatDate value="${bid.timestamp}" pattern="dd-MM-yyyy "/>
                        <fmt:formatDate value="${bid.timestamp}" pattern=" HH:mm "/>
                            ${bid.user.username}
                            ${bid.amount} CHF
                    </td>

                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="clearBoth"></div>
    <br>


    <table id="adDescTable" class="adDescDiv">
        <tr>
            <td><h2>Type</h2></td>
            <td>
                <c:choose>
                    <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                    <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                    <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Address</h2></td>
            <td>
                <a class="link"
                   href="http://maps.google.com/?q=${shownAd.street}, ${shownAd.zipcode}, ${shownAd.city}">${shownAd.street},
                    ${shownAd.zipcode} ${shownAd.city}</a>
            </td>
        </tr>

        <tr>
            <td><h2>Available from</h2></td>
            <td>${formattedMoveInDate}</td>
        </tr>

        <tr>
            <td><h2>Monthly Rent</h2></td>
            <td>${shownAd.price}&#32;CHF</td>
        </tr>

        <tr>
            <td><h2>Square Meters</h2></td>
            <td>${shownAd.squareFootage}&#32;m²</td>
        </tr>
        <tr>
            <td><h2>Ad created on</h2></td>
            <td>${formattedCreationDate}</td>
        </tr>
    </table>
</section>

<div id="image-slider">
    <div id="left-arrow">
        <img src="/img/left-arrow.png"/>
    </div>
    <div id="images">
        <c:forEach items="${shownAd.pictures}" var="picture">
            <img src="${picture.filePath}"/>
        </c:forEach>
    </div>
    <div id="right-arrow">
        <img src="/img/right-arrow.png"/>
    </div>
</div>

<hr class="clearBoth"/>

<section>
    <div id="descriptionTexts">
        <div class="adDescDiv">
            <h2>Room Description</h2>
            <p>${shownAd.roomDescription}</p>
        </div>
        <br/>


        <div class="adDescDiv">
            <h2>Preferences</h2>
            <p>${shownAd.preferences}</p>
        </div>
        <br/>

        <div id="visitList" class="adDescDiv">
            <h2>Visiting times</h2>
            <table>
                <c:forEach items="${visits }" var="visit">
                    <tr>
                        <td>
                            <fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy "/>
                            &nbsp; from
                            <fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm "/>
                            until
                            <fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm"/>
                        </td>
                        <td><c:choose>
                            <c:when test="${loggedIn}">
                                <c:if test="${loggedInUserEmail != shownAd.user.username}">
                                    <button class="thinButton" type="button" data-id="${visit.id}">Send
                                        enquiry to advertiser
                                    </button>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <a href="/login">
                                    <button class="thinInactiveButton" type="button"
                                            data-id="${visit.id}">Login to send enquiries
                                    </button>
                                </a>
                            </c:otherwise>
                        </c:choose></td>
                    </tr>
                </c:forEach>
            </table>
        </div>


    </div>

    <table id="checkBoxTable" class="adDescDiv">
        <tr>
            <td><h2>Smoking inside allowed</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.smokers}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Animals allowed</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.animals}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Furnished Room</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.furnished}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Garage</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.garage}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Cellar</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.cellar}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Balcony</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.balcony}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

        <tr>
            <td><h2>Garden</h2></td>
            <td>
                <c:choose>
                    <c:when test="${shownAd.garden}"><img src="/img/check-mark.png"></c:when>
                    <c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
                </c:choose>
            </td>
        </tr>

    </table>
</section>

<div class="clearBoth"></div>
<br>

<table id="advertiserTable" class="adDescDiv">
    <tr>
        <td><h2>Advertiser</h2><br/></td>
    </tr>

    <tr>
        <td><c:choose>
            <c:when test="${shownAd.user.picture.filePath != null}">
                <img src="${shownAd.user.picture.filePath}">
            </c:when>
            <c:otherwise>
                <img src="/img/avatar.png">
            </c:otherwise>
        </c:choose></td>

        <td>${shownAd.user.username}</td>

        <td id="advertiserEmail">
            <c:choose>
            <c:when test="${loggedIn}">
            <a href="/user?id=${shownAd.user.id}">
                <button type="button">Visit profile</button>
            </a>
            </c:when>
            <c:otherwise>
            <a href="/login">
                <button class="thinInactiveButton" type="button">Login to visit profile</button>
            </a>
            </c:otherwise>
            </c:choose>

        <td>
            <form>
                <c:choose>
                    <c:when test="${loggedIn}">
                        <c:if test="${loggedInUserEmail != shownAd.user.username }">
                            <button id="newMsg" type="button">Contact Advertiser</button>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <a href="/login">
                            <button class="thinInactiveButton" type="button">Login to contact advertiser</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </form>
        </td>
    </tr>
</table>

<div id="msgDiv">
    <form class="msgForm">
        <h2>Contact the advertiser</h2>
        <br>
        <br>
        <label>Subject: <span>*</span></label>
        <input class="msgInput" type="text" id="msgSubject" placeholder="Subject"/>
        <br><br>
        <label>Message: </label>
        <textarea id="msgTextarea" placeholder="Message"></textarea>
        <br/>
        <button type="button" id="messageSend">Send</button>
        <button type="button" id="messageCancel">Cancel</button>
    </form>
</div>

<div id="confirmationDialog">
    <form>
        <p>Send enquiry to advertiser?</p>
        <button type="button" id="confirmationDialogSend">Send</button>
        <button type="button" id="confirmationDialogCancel">Cancel</button>
    </form>
</div>


<c:import url="template/footer.jsp"/>