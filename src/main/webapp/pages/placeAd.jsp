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
    // INject error message for city
    $(document).ready(function () {

        // Go to controller take what you need from user
        // save it to a hidden field
        // iterate through it
        // if there is id == x then make "Bookmark Me" to "bookmarked"

        $("#field-city").autocomplete({
            minLength: 2
        });

        citys = <c:import url="getzipcodes.jsp" />;
        $("#field-city").autocomplete({
            source: citys });
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
    });

    var injected = false;
    function isValid() {
        var valid = false;
        citys.forEach(function (entry) {
           if($("#field-city").val().trim() == entry.toString().trim()){
               valid = true;
           }
        });
        if(valid) return true;
        if(!injected){
            $("#field-city").after("<span id=\"city.errors\" class=\"validationErrorText\">Please pick a city from the list</span>");
            injected = true;
        }
        return false;
    }
</script>

<script>
    function checkForCreditCard() {
        var premiumAd = document.getElementById("premium");
        if (!${hasCreditCard}) {
            premiumAd.checked = false;
            $("#premiumAdErrorDiv").html("You need a credit card to place a premium ad.")
        }
    }
</script>

<!--<pre>
<a href="/">Home</a> &gt; Place ad</pre>-->

<div class="container">

<row>
    <h1>Place an ad</h1>
    <hr/>




    <form:form method="post" modelAttribute="placeAdForm"
               action="/profile/placeAd" id="placeAdForm" autocomplete="off"
               enctype="multipart/form-data" onsubmit="return isValid();">

    <div class="table-responsive">
    <fieldset>
            <legend>General info</legend>
                <table class="table table-striped">
                <tr>
                    <td><label for="field-title">Ad Title</label></td>
                    <td><label for="type-house">Type:</label></td>
                </tr>

                <tr>
                    <td><form:input id="field-title" path="title" placeholder="Ad Title"/>
                        <form:errors path="title" cssClass="validationErrorText"/>
                    </td>
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
                    <td><form:input id="field-street" path="street" placeholder="Street"/>
                        <form:errors path="street" cssClass="validationErrorText"/></td>
                    <td><form:input id="field-city" path="city" placeholder="City"/>
                        <form:errors path="city" cssClass="validationErrorText"/></td>
                </tr>

                <tr>
                    <td><label for="moveInDate">Move-in date</label></td>
                </tr>
                <tr>
                    <td><form:input type="text" id="field-moveInDate"
                                    path="moveInDate"/> <form:errors
                            path="moveInDate" cssClass="validationErrorText"/></td>


                </tr>

                <tr>
                    <td><label for="field-Prize">Starting price</label></td>
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
                                       // $("#field-InstantBuyPrice").val(0);
                                    }
                                });
                            }
                        </script>
                        <br/>

                         <button type="button" id="instbtn" class="btn" onclick="showHideInstantBuy()">Add Instant Buy Price</button>
                        <form:errors
                                path="instantBuyPrice" cssClass="validationErrorText"/>
                    </td>
                </tr>

                <tr class="instbtn" style="display: none">
                    <td><label for="field-InstantBuyPrice">Instant Buy Price</label></td>
                </tr >

                <tr class="instbtn" style="display: none">
                    <td>
                        <form:input id="field-InstantBuyPrice" type="number" value="0" path="instantBuyPrice"/></td>
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
                    <td><form:checkbox id="field-dishwasher" path="dishwasher" value="1"/><label>Dishwasher</label>
                    </td>
                </tr>
                <tr>
                    <td><form:checkbox id="field-washingMachine" path="washingMachine" value="1"/><label>Washing machine</label>
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


                        <div id="addVisitButton" class="smallPlusButton">Add Visiting Time</div>

                        <div id="addedVisits"></div>
                    </td>

                </tr>

            </table>
            <br>
        </fieldset>

        <fieldset>
            <legend>Premium Ad</legend>
            <div id="premiumAdErrorDiv" style="color: #cc0000;">
            </div>
            <table>
                <tr>
                    <td><form:checkbox path="premium" id="premium" value="0" onclick="checkForCreditCard()"/></td>
                    <td>Premium Ad</td>
                </tr>
                <tr>
                    <td colspan="2">Do you want to place a premium Ad? Please be informed that this is not for free
                        The charges are 5$ per Premium Ad. Your advantages are that you Ad will be shown
                        on top of the site if someone searches and your Ad is inside the parameters
                    </td>
                </tr>
            </table>
        </fieldset>
        </div>


        <br/>
        <div>
            <button type="submit">Submit</button>
            <a href="/">
                <button type="button">Cancel</button>
            </a>
        </div>
    </row>

    </form:form>
</div>

<c:import url="template/footer.jsp"/>
