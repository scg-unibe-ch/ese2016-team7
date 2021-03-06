<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script src="/js/pictureUploadEditAd.js"></script>

<script src="/js/editAd.js"></script>


<script>
        $(document).ready(function () {
            $("#field-city").autocomplete({
                minLength: 2
            });
            citys = <c:import url="getzipcodes.jsp" />;
            $("#field-city").autocomplete({
                source: citys
            });
            $("#field-city").autocomplete("option", {
                enabled: true,
                autoFocus: true
            });
            $("#field-moveInDate").datepicker({
                dateFormat: 'dd-mm-yy'
            });
            $("#field-moveOutDate").datepicker({
                dateFormat: 'dd-mm-yy'
            });

            $("#field-visitDay").datepicker({
                dateFormat: 'dd-mm-yy'
            });

            $("#addbutton").click(function () {
                var text = $("#roomFriends").val();
                var alreadyAdded = $("#addedRoommates").html();
                if (validateForm(text)) {
                    $.post("/profile/placeAd/validateEmail", {email: text, alreadyIn: alreadyAdded}, function (data) {
                        if (validateForm(data)) {
                            // length gibt die Anzahl der Elemente im input.roommateInput an. Dieser wird in index geschrieben und iteriert.
                            var index = $("#roommateCell input.roommateInput").length;
                            $("#roommateCell").append("<input class='roommateInput' type='hidden' name='registeredRoommateEmails[" + index + "]' value='" + data + "' />");
                            $("#addedRoommates").append(data + "; ");
                        } else {
                            alert(data);
                        }
                    });
                }
                else {
                    alert("Please enter an e-mail adress");
                }

                // Validates the input for Email Syntax
                function validateForm(text) {
                    var positionAt = text.indexOf("@");
                    var positionDot = text.lastIndexOf(".");
                    if (positionAt < 1 || positionDot < positionAt + 2 || positionDot + 2 >= text.length) {
                        return false;
                    } else {
                        return true;
                    }
                }
            });

            $("#addVisitButton").click(function () {
                var date = $("#field-visitDay").val();
                if (date == "") {
                    alert("Please pick a date.");
                    return;
                }

                var startHour = $("#startHour").val();
                var startMinutes = $("#startMinutes").val();
                var endHour = $("#endHour").val();
                var endMinutes = $("#endMinutes").val();

                if (startHour > endHour) {
                    alert("Invalid times. The visit can't end before being started.");
                    return;
                } else if (startHour == endHour && startMinutes >= endMinutes) {
                    alert("Invalid times. The visit can't end before being started.");
                    return;
                }

                var newVisit = date + ";" + startHour + ":" + startMinutes +
                        ";" + endHour + ":" + endMinutes;
                var newVisitLabel = date + " " + startHour + ":" + startMinutes +
                        " to " + endHour + ":" + endMinutes;

                var dateParts = date.split("-");
                var dateCheck = new Date(dateParts[1]+"-"+dateParts[0]+"-"+dateParts[2]);
                console.log(dateCheck.toString());
                if(!(/^[0-9]{2}-[0-9]{2}-[0-9]{4}/.test(date.toString()))){
                    alert("No valid date! Please enter valid date. Wrong Pattern");
                    return;
                }
                if(isNaN(dateCheck.getTime())){

                    alert("No valid date. Please enter valid date.");
                    return;
                }

                var now = new Date();
                if(dateCheck.getTime()<=now.getTime()){
                    alert("Visits in the past don't help anyone. Try one from the future.");
                    return;
                }


                var index = $("#addedVisits input").length;

                var label = "<p>" + newVisitLabel + "</p>";
                var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";

                $("#addedVisits").append(label + input);
            });

            $(".deleteRoommateButton").click(function () {
                var userId = $(this).attr("data-user-id");
                var adId = $(this).attr("data-ad-id");
                var row = $(this).parent().parent();
                $.post("/profile/editAd/deleteRoommate", {userId: userId, adId: adId}, function () {
                    $(row).animate({opacity: 0}, 300, function () {
                        $(row).remove();
                    });
                });

            });
        });
        var injected = false;
    function isValid() {
        var valid = false;
        citys.forEach(function (entry) {
            if($("#city").val().trim() == entry.toString().trim()){
                valid = true;
            }
        });
        if(valid) return true;
        if(!injected){
            $("#city").after("<span id=\"city.errors\" class=\"validationErrorText\">Please pick a city from the list</span>");
            injected = true;
        }
        return false;
    }


    </script>

<!-- format the dates -->
<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                type="date" pattern="dd-MM-yyyy"/>

<!--<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   <a href="/ad?id=${ad.id}">Ad Description</a>   &gt;   Edit Ad</pre>-->

<div class="container">


    <h1>Edit Ad</h1>
    <hr/>
    <form:form method="post" modelAttribute="placeAdForm"
               action="/profile/editAd" id="placeAdForm" autocomplete="off"
               enctype="multipart/form-data" onsubmit="return isValid();">
        <input type="hidden" name="adId" value="${ad.id}"/>
    <p style="color:red; margin-left: 2em;">* required</p>

    <div class="table-responsive">

    <fieldset>
            <legend>Change General info <span style="color:red">*</span></legend>
                    <table class="table table-striped">
                <tr>
                    <td><label for="field-title">Ad Title <span style="color:red">*</span></label></td>
                    <td colspan="3"><label for="type-house">Type: <span style="color:red">*</span></label></td>
                </tr>
                <tr>
                    <td><form:input id="field-title" path="title" value="${ad.title}"/></td>
                    <td><form:radiobutton id="type-house" path="property" value="HOUSE" checked="checked"/>House</td>
                    <td><form:radiobutton id="type-studio" path="property" value="STUDIO"/>Studio</td>
                    <td><form:radiobutton id="type-apartment" path="property" value="APARTMENT"/>Apartment</td>
                </tr>

                <tr>
                    <td><label for="field-street">Street <span style="color:red">*</span></label></td>
                    <td><label for="field-city">City / Zip code <span style="color:red">*</span></label></td>
                </tr>
                <tr>
                    <td><form:input id="field-street" path="street"
                                    value="${ad.street}" cssClass="form-control"/></td>
                    <td>
                        <form:input id="field-city" path="city" value="${ad.zipcode} - ${ad.city}" cssClass="form-control"/>
                        <form:errors path="city" cssClass="validationErrorText"/>
                    </td>
                </tr>
                <tr>
                    <td><label for="moveInDate">Move-in date <span style="color:red">*</span></label></td>
                </tr>
                <tr>
                    <td>
                        <form:input type="text" id="field-moveInDate"
                                    path="moveInDate" value="${formattedMoveInDate }" cssClass="form-control"/>
                    </td>
                </tr>
                <tr>
                    <td><label for="field-Prize">Starting Price <span style="color:red">*</span></label></td>
                    <td><label for="field-SquareFootage">Square Meters <span style="color:red">*</span></label></td>
                    <td><label for="field-NumberRooms">Number of Rooms <span style="color:red">*</span></label></td>
                </tr>
                <tr>
                    <td>
                        <form:input id="field-Prize" type="number" path="price"
                                     step="50" value="${ad.price}" cssClass="form-control"/>
                        <form:errors path="price" cssClass="validationErrorText"/>
                    </td>
                    <td><form:input id="field-SquareFootage" type="number" path="squareFootage" placeholder="Price per month" step="5" value="${ad.squareFootage }" cssClass="form-control"/>
                        <form:errors path="squareFootage" cssClass="validationErrorText"/>
                    </td>
                    <td><form:input id="field-NumberRooms" type="number" path="numberRooms" placeholder="Price per month" step="0.5" value="${ad.numberRooms}" cssClass="form-control"/>
                        <form:errors path="numberRooms" cssClass="validationErrorText"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <script type="text/javascript">
                            function showHideInstantBuy() {
                                $(".instbtn").each(function () {
                                    if ($(this).css("display") == "none") {
                                        $(this).css("display", "");
                                        $("#instbtn").html("Remove Instant Buy Price");
                                    }else{
                                        $(this).css("display", "none");
                                        $("#instbtn").html("Add Instant Buy Price");
                                        $("#field-InstantBuyPrice").val(0);
                                    }
                                });
                            }
                        </script>
                        <br/>
                        <button type="button" id="instbtn" class="btn" onclick="showHideInstantBuy()">
                            <c:choose><c:when test="${ad.instantBuyPrice > 0}">Remove Instant Buy Price</c:when><c:otherwise>Add Instant Buy Price</c:otherwise></c:choose>
                        </button>
                    </td>
                </tr>
                <tr class="instbtn" style="display: <c:choose><c:when test="${ad.instantBuyPrice == 0}">none</c:when></c:choose>">
                    <td><label for="field-InstantBuyPrice">Instant Buy Price</label></td>
                </tr >
                <tr class="instbtn" style="display: <c:choose><c:when test="${ad.instantBuyPrice == 0}">none</c:when></c:choose>">
                    <td><form:input id="field-InstantBuyPrice" type="number" path="instantBuyPrice" step="50" value="${ad.instantBuyPrice}"/></td>
                </tr>
            </table>
        </fieldset>


        <br/>
        <fieldset>
            <legend>Change Room Description <span style="color:red">*</span></legend>
            <table class="placeAdTable">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${ad.smokers}">
                                <form:checkbox id="field-smoker" path="smokers" checked="checked" cssClass="checkbox"/>
                                <label>Smoking inside allowed</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-smoker" path="smokers" cssClass="checkbox"/><label>Smoking
                                inside allowed</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${ad.animals}">
                                <form:checkbox id="field-animals" path="animals" checked="checked" cssClass="checkbox"/>
                                <label>Animals allowed</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-animals" path="animals" cssClass="checkbox"/>
                                <label>Animals allowed</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${ad.garden}">
                                <form:checkbox id="field-garden" path="garden" checked="checked" cssClass="checkbox"/>
                                <label>Garden (co-use)</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-garden" path="garden" cssClass="checkbox"/>
                                <label>Garden (co-use)</label>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${ad.balcony}">
                                <form:checkbox id="field-balcony" path="balcony" checked="checked" cssClass="checkbox"/>
                                <label>Balcony or Patio</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-balcony" path="balcony" cssClass="checkbox"/>
                                <label>Balcony or Patio</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${ad.cellar}">
                                <form:checkbox id="field-cellar" path="cellar" checked="checked"/>
                                <label>Cellar or Attic</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-cellar" path="cellar" cssClass="checkbox"/>
                                <label>Cellar or Attic</label>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${ad.furnished}">
                                <form:checkbox id="field-furnished" path="furnished" checked="checked" cssClass="checkbox"/>
                                <label>Furnished</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-furnished" path="furnished" cssClass="checkbox"/>
                                <label>Furnished</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${ad.garage}"><form:checkbox id="field-garage" path="garage" checked="checked" cssClass="checkbox"/>
                                <label>Garage</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-garage" path="garage" cssClass="checkbox"/>
                                <label>Garage</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${ad.dishwasher}"><form:checkbox id="field-dishwasher" path="dishwasher" checked="checked" cssClass="checkbox"/>
                                <label>Dishwasher</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-dishwasher" path="dishwasher" cssClass="checkbox"/>
                                <label>Dishwasher</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${ad.washingMachine}"><form:checkbox id="field-washingMachine" path="washingMachine" checked="checked" cssClass="checkbox"/>
                                <label>Washing Machine</label>
                            </c:when>
                            <c:otherwise>
                                <form:checkbox id="field-washingMachine" path="washingMachine" cssClass="checkbox"/>
                                <label>Washing Machine</label>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>


            </table>
            <br/>
            <form:textarea path="roomDescription" rows="10" cols="100" value="${ad.roomDescription}" cssClass="form-control"/>
            <form:errors path="roomDescription" cssClass="validationErrorText"/>
        </fieldset>


        <br/>

        <fieldset>
            <legend>Edit visiting times</legend>
            <h3>Add visiting times</h3>
            <table>
                <tr>
                    <td>
                        <input type="text" id="field-visitDay"/>
                        <select id="startHour">
                            <%
                                for (int i = 0; i < 24; i++) {
                                    String hour = String.format("%02d", i);
                                    out.print("<option value=\"" + hour + "\">" + hour + "</option>");
                                }
                            %>
                        </select>
                        <select id="startMinutes">
                            <%
                                for (int i = 0; i < 60; i++) {
                                    String minute = String.format("%02d", i);
                                    out.print("<option value=\"" + minute + "\">" + minute + "</option>");
                                }
                            %>
                        </select>
                        <span>to&thinsp; </span>
                        <select id="endHour">
                            <%
                                for (int i = 0; i < 24; i++) {
                                    String hour = String.format("%02d", i);
                                    out.print("<option value=\"" + hour + "\">" + hour + "</option>");
                                }
                            %>
                        </select>

                        <select id="endMinutes">
                            <%
                                for (int i = 0; i < 60; i++) {
                                    String minute = String.format("%02d", i);
                                    out.print("<option value=\"" + minute + "\">" + minute + "</option>");
                                }
                            %>
                        </select>
                        <div id="addVisitButton" class="smallPlusButton">Add Visiting Time</div>
                        <div id="addedVisits"></div>
                    </td>
                </tr>
            </table>
            <hr/>
            <h3>Delete visiting times</h3>
            <div>
                <c:forEach items="${visits}" var="visit">
                    <div class="visit">
                        <div>
                            <fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy"/>
                            &nbsp; from
                            <fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm "/>
                            until
                            <fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm"/>
                        </div>
                        <button type="button" data-visit-ad-id="${ad.id }" data-visit-id="${visit.id }">Delete</button>
                    </div>
                </c:forEach>
            </div>
        </fieldset>

        <br/>

         <fieldset>
             <legend>Change pictures</legend>
             <h3>Delete existing pictures</h3>
             <br/>
             <div>
                 <c:forEach items="${ad.pictures }" var="picture">
                     <div class="pictureThumbnail">
                         <div>
                             <img src="${picture.filePath}"/>
                         </div>
                         <button type="button" data-ad-id="${ad.id }" data-picture-id="${picture.id }">Delete
                         </button>
                     </div>
                 </c:forEach>
             </div>
             <p class="clearBoth"></p>
             <br/><br/>
             <hr/>
             <h3>Add new pictures</h3>
             <br/>
             <label for="field-pictures">Pictures</label>
             <input type="file" id="field-pictures" accept="image/*" multiple="multiple" class="form-control"/>
             <table class="table" id="uploaded-pictures" class="styledTable">
                 <tr>
                     <th id="name-column">Uploaded picture</th>
                     <th>Size</th>
                     <th>Delete</th>
                 </tr>
             </table>
             <br>
         </fieldset>

        <div>
            <button type="submit" class="btn btn-default" >Submit</button>
            <a href="<c:url value='/ad?id=${ad.id}' />">
                <button type="button" class="btn btn-default">Cancel</button>
            </a>
        </div>
    </form:form>
</div>
        </div>

    </row>



<c:import url="template/footer.jsp"/>
