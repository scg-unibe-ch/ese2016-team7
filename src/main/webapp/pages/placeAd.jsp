<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script src="/js/pictureUpload.js"></script>


<script>
    $(document).ready(function () {

        // Go to controller take what you need from user
        // save it to a hidden field
        // iterate through it
        // if there is id == x then make "Bookmark Me" to "bookmarked"

        $("#field-city").autocomplete({
            minLength: 2
        });
        $("#field-city").autocomplete({
            source: <c:import url="getzipcodes.jsp" />
        });
        $("#field-city").autocomplete("option", {
            enabled: true,
            autoFocus: true
        });
        $("#field-moveInDate").datepicker({
            dateFormat: 'dd-mm-yy'
        });

        $("#field-visitDay").datepicker({
            dateFormat: 'dd-mm-yy'
        });


        $("#addVisitButton").click(function () {
            var date = $("#field-visitDay").val();
            if (date == "") {
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

            var index = $("#addedVisits input").length;

            var label = "<p>" + newVisitLabel + "</p>";
            var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";

            $("#addedVisits").append(label + input);
        });
    });
</script>

<!--<pre>
<a href="/">Home</a> &gt; Place ad</pre>-->

<div class="container">


    <h1>Place an ad</h1>
    <hr/>

    <form:form method="post" modelAttribute="placeAdForm"
               action="/profile/placeAd" id="placeAdForm" autocomplete="off"
               enctype="multipart/form-data">

        <fieldset>
            <legend>General info</legend>
            <table class="placeAdTable">
                <tr>
                    <td><label for="field-title">Ad Title</label></td>
                    <td><label for="type-house">Type:</label></td>
                </tr>

                <tr>
                    <td><form:input id="field-title" path="title" placeholder="Ad Title"/></td>
                    <td><form:radiobutton id="type-house" path="property" value="HOUSE"
                                          checked="checked"/>House
                    </td>
                    <td><form:radiobutton id="type-studio" path="property" value="STUDIO"/>Studio</td>
                    <td><form:radiobutton id="type-apartment" path="property" value="APARTMENT"/>Apartment</td>
                </tr>

                <tr>
                    <td><label for="field-street">Street</label></td>
                    <td><label for="field-city">City / Zip code</label></td>
                </tr>

                <tr>
                    <td><form:input id="field-street" path="street" placeholder="Street"/></td>
                    <td><form:input id="field-city" path="city" placeholder="City"/>
                        <form:errors path="city" cssClass="validationErrorText"/></td>
                </tr>

                <tr>
                    <td><label for="moveInDate">Move-in date</label></td>
                </tr>
                <tr>
                    <td><form:input type="text" id="field-moveInDate"
                                    path="moveInDate"/></td>
                </tr>

                <tr>
                    <td><label for="field-Prize">Auction price</label></td>
                    <td><label for="field-SquareFootage">Square Meters</label></td>
                    <td><label for="field-NumberRooms">Number of Rooms</label></td>
                </tr>
                <tr>
                    <td><form:input id="field-Prize" type="number" path="price"
                                    placeholder="price" step="50"/> <form:errors
                            path="price" cssClass="validationErrorText"/></td>
                    <td><form:input id="field-SquareFootage" type="number"
                                    path="squareFootage" placeholder="price" step="5"/> <form:errors
                            path="squareFootage" cssClass="validationErrorText"/></td>
                    <td><form:input id="field-NumberRooms" type="number"
                                    path="numberRooms" placeholder="price" step="0.5"/> <form:errors
                            path="numberRooms" cssClass="validationErrorText"/></td>
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
                        <button type="button" id="instbtn" class="btn" onclick="showHideInstantBuy()">Add Instant Buy Price</button>
                    </td>
                </tr>
                <tr class="instbtn" style="display: none">
                    <td><label for="field-InstantBuyPrice">Instant Buy Price</label></td>
                </tr >
                <tr class="instbtn" style="display: none">
                    <td><form:input id="field-InstantBuyPrice" type="number" path="instantBuyPrice" step="50"/></td>
                </tr>
            </table>
        </fieldset>
        <br/>
        <fieldset>
            <legend>Room Description</legend>

            <table class="placeAdTable">
                <tr>
                    <td><form:checkbox id="field-smoker" path="smokers" value="1"/><label>Animals
                        allowed</label></td>
                    <td><form:checkbox id="field-animals" path="animals" value="1"/><label>Smoking
                        inside allowed</label></td>
                </tr>
                <tr>
                    <td><form:checkbox id="field-garden" path="garden" value="1"/><label>Garden
                        (co-use)</label></td>
                    <td><form:checkbox id="field-balcony" path="balcony" value="1"/><label>Balcony
                        or Patio</label></td>
                </tr>
                <tr>
                    <td><form:checkbox id="field-cellar" path="cellar" value="1"/><label>Cellar
                        or Attic</label></td>
                    <td><form:checkbox id="field-furnished" path="furnished"
                                       value="1"/><label>Furnished</label></td>
                </tr>
                <tr>
                    <td><form:checkbox id="field-garage" path="garage" value="1"/><label>Garage</label>
                    </td>
                </tr>

            </table>
            <br/>
            <form:textarea path="roomDescription" rows="10" cols="100"
                           placeholder="Room Description"/>
            <form:errors path="roomDescription" cssClass="validationErrorText"/>
        </fieldset>

        <br/>
        <br/>

        <fieldset>
            <legend>Pictures (optional)</legend>
            <br/> <label for="field-pictures">Pictures</label> <input
                type="file" id="field-pictures" accept="image/*" multiple="multiple"/>
            <table id="uploaded-pictures" class="styledTable">
                <tr>
                    <th id="name-column">Uploaded picture</th>
                    <th>Size</th>
                    <th>Delete</th>
                </tr>
            </table>
            <br>
        </fieldset>

        <fieldset>
            <legend>Visiting times (optional)</legend>

            <table>
                <tr>
                    <td><input type="text" id="field-visitDay"/> <select
                            id="startHour">
                        <%
                            for (int i = 0; i < 24; i++) {
                                String hour = String.format("%02d", i);
                                out.print("<option value=\"" + hour + "\">" + hour
                                        + "</option>");
                            }
                        %>
                    </select> <select id="startMinutes">
                        <%
                            for (int i = 0; i < 60; i++) {
                                String minute = String.format("%02d", i);
                                out.print("<option value=\"" + minute + "\">" + minute
                                        + "</option>");
                            }
                        %>
                    </select> <span>to&thinsp; </span> <select id="endHour">
                        <%
                            for (int i = 0; i < 24; i++) {
                                String hour = String.format("%02d", i);
                                out.print("<option value=\"" + hour + "\">" + hour
                                        + "</option>");
                            }
                        %>
                    </select> <select id="endMinutes">
                        <%
                            for (int i = 0; i < 60; i++) {
                                String minute = String.format("%02d", i);
                                out.print("<option value=\"" + minute + "\">" + minute
                                        + "</option>");
                            }
                        %>
                    </select>


                        <div id="addVisitButton" class="smallPlusButton">+</div>

                        <div id="addedVisits"></div>
                    </td>

                </tr>

            </table>
            <br>
        </fieldset>

        <fieldset>
            <legend>Premium Ad</legend>
            <table>
                <tr>
                    <td><form:checkbox path="premium" value="0"/></td>
                    <td>Premium Ad</td>
                </tr>
                <tr>
                    <td><form:input type="number" path="securityCode"/></td>
                    <td>Security Code for Credit Card</td>
                </tr>
                <tr>
                    <td colspan="2">Do you want to place a premium Ad? Please be informed that this is not for free
                        The charges are 5$ per Premium Ad. Your advantages are that you Ad will be shown
                        on top of the site if someone searches and your Ad is inside the parameters
                    </td>
                </tr>
            </table>
        </fieldset>


        <br/>
        <div>
            <button type="submit">Submit</button>
            <a href="/">
                <button type="button">Cancel</button>
            </a>
        </div>

    </form:form>
</div>

<c:import url="template/footer.jsp"/>
