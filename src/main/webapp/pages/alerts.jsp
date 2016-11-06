<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>

<!--<pre><a href="/">Home</a> &gt; Alerts</pre>-->
<script>
    function deleteAlert(button) {
        var id = $(button).attr("data-id");
        $.get("/profile/alerts/deleteAlert?id=" + id, function () {
            $("#alertsDiv").load(document.URL + " #alertsDiv");
        });
    }
</script>

<script>
    function validateType(form) {
        var house = document.getElementById('house')
        var studio = document.getElementById('studio');
        var apartment = document.getElementById('apartment');
        var neither = document.getElementById('neither');
        var bothHouseAndStudio = document.getElementById('bothHouseAndStudio');

        if (house.checked && studio.checked) {
            bothHouseAndStudio.checked = true;
            neither.checked = false;
        }
        else if (!house.checked && !studio.checked) {
            bothHouseAndStudio.checked = false;
            neither.checked = true;
        }
        else {
            bothHouseAndStudio.checked = false;
            neither.checked = false;
        }
    }
</script>

<script>
    $(document).ready(function () {
        $("#city").autocomplete({
            minLength: 2
        });
        $("#city").autocomplete({
            source: <c:import url="getzipcodes.jsp" />
        });
        $("#city").autocomplete("option", {
            enabled: true,
            autoFocus: true
        });

        var price = document.getElementById('priceInput');
        var radius = document.getElementById('radiusInput');

        if (price.value == null || price.value == "" || price.value == "0")
            price.value = "500";
        if (radius.value == null || radius.value == "" || radius.value == "0")
            radius.value = "5";
    });

</script>
<script>
    isChecked(check)
    {

        return "true";
    }
</script>

<div class="container">
    <h1>Manage alerts</h1>
    <hr/>

    <div id="alertsDiv" class="alertsDiv">
        <c:choose>
            <c:when test="${empty alerts}">
                <p>You currently aren't subscribed to any alerts.
            </c:when>
            <c:otherwise>
                <table class="styledTable" id="alerts">
                    <thead>
                    <tr>
                        <th>Type</th>
                        <th>City</th>
                        <th>Radius</th>
                        <th>max. Price</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <c:forEach var="alert" items="${alerts}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${alert.apartment && alert.house && alert.studio}">
                                        Apartment, House & Studio
                                    </c:when>
                                    <c:when test="${alert.apartment && alert.house}">
                                        Apartment & House
                                    </c:when>
                                    <c:when test="${alert.apartment && alert.studio}">
                                        Apartment & Studio
                                    </c:when>
                                    <c:when test="${alert.house && alert.studio}">
                                        House & Studio
                                    </c:when>
                                    <c:when test="${alert.studio}">
                                        Studio
                                    </c:when>
                                    <c:when test="${alert.house}">
                                        House
                                    </c:when>
                                    <c:when test="${alert.apartment}">
                                        Apartment
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>${alert.city}</td>
                            <td>${alert.radius} km</td>
                            <td>${alert.price} Chf</td>
                            <form:form method="post" modelAttribute="searchForm" action="/results" id="searchForm"
                                       autocomplete="off">

                            <c:choose>
                                <c:when test="${alert.house}">
                                    <form:checkbox style="opacity:0;" name="house" id="house" path="house"
                                                   checked="ajsndo"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.studio}">
                                    <form:checkbox style="opacity:0;" name="studio" id="studio" path="studio"
                                                   checked="kahsdbflhbd"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.apartment}">
                                    <form:checkbox style="opacity:0;" name="apartment" id="apartment"
                                                   path="apartment" checked="jsndiusdf"/>
                                </c:when>
                            </c:choose>
                            <form:input type="hidden" name="city" id="city" path="city" placeholder="e.g. Bern	"
                                        tabindex="3" value="${alert.city}"/>
                            <form:input id="radiusInput" type="hidden" path="radius" placeholder="e.g. 5" step="5"
                                        value="${alert.radius}"/>
                            <form:input id="prizeInput" type="hidden" path="price" placeholder="e.g. 5" step="50"
                                        value="${alert.price}"/>
                            <form:input type="hidden" id="field-earliestMoveInDate" path="earliestMoveInDate"
                                        value="${alert.getEarliestMoveInDate()}"/>
                            <form:input type="hidden" id="field-latestMoveInDate" path="latestMoveInDate"
                                        value="${alert.latestMoveInDate}"/>

                            <c:choose>
                                <c:when test="${alert.smokers}">
                                    <form:checkbox style="opacity:0;" id="field-smoker" path="smokers"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>

                            <c:choose>
                                <c:when test="${alert.animals}">
                                    <form:checkbox style="opacity:0;" id="field-animals" path="animals"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.garden}">
                                    <form:checkbox style="opacity:0;" id="field-garden" path="garden"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.balcony}">
                                    <form:checkbox style="opacity:0;" id="field-balcony" path="balcony"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.cellar}">
                                    <form:checkbox style="opacity:0;" id="field-cellar" path="cellar"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.furnished}">
                                    <form:checkbox style="opacity:0;" id="field-furnished" path="furnished"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${alert.garage}">
                                    <form:checkbox style="opacity:0;" id="field-garage" path="garage"
                                                   checked="whatever"/>
                                </c:when>
                            </c:choose>

                            <td>
                                <button type="submit" onClick="form.action='/results';">Search</button>
                                </form:form>
                                <button class="deleteButton" data-id="${alert.id}" onClick="deleteAlert(this)">
                                    Delete
                                </button>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<c:import url="template/footer.jsp"/>