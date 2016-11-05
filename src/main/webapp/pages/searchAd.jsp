<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>


<pre><a href="/">Home</a>   &gt;   Search</pre>

<div class="container-fluid">

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

        $("#field-earliestMoveInDate").datepicker({
            dateFormat: 'dd-mm-yy'
        });
        $("#field-latestMoveInDate").datepicker({
            dateFormat: 'dd-mm-yy'
        });
        $("#field-earliestMoveOutDate").datepicker({
            dateFormat: 'dd-mm-yy'
        });
        $("#field-latestMoveOutDate").datepicker({
            dateFormat: 'dd-mm-yy'
        });

        var price = document.getElementById('prizeInput');
        var radius = document.getElementById('radiusInput');

        if (price.value == null || price.value == "" || price.value == "0")
            price.value = "500";
        if (radius.value == null || radius.value == "" || radius.value == "0")
            radius.value = "5";
    });
</script>




<script>
    function advancedSearch() {
        if($("#advanced").css("display") == "none") {
            $("#advanced").css("display","");
            $("#advancedSearch").html("Hide Advanced Search");
        }else {
            $("#advanced").css("display", "none");
            $("#advancedSearch").html("Show Advanced Search");
        }
    }

    function markAllPropertiesIfNoneIsMarked() {
        var house = document.getElementById('house');
        var apartment = document.getElementById('apartment');
        var studio = document.getElementById('studio');
        if(!house.checked && !apartment.checked && !studio.checked) {
            house.checked = true;
            apartment.checked = true;
            studio.checked = true;
        }
    }
</script>

<h1>Search for an ad</h1>
<hr/>
<form:form method="post" modelAttribute="searchForm" action="/results"
           id="searchForm" autocomplete="off">
    <fieldset>
        <form:checkbox name="house" id="house" path="house"/><label>House</label>
        <form:checkbox name="studio" id="studio" path="studio"/><label>Studio</label>
        <form:checkbox name="apartment" id="apartment" path="apartment"/><label>Apartment</label>


        <br />

        <label for="city">City / zip code:</label>
        <form:input type="text" name="city" id="city" path="city"
                    placeholder="e.g. Bern" tabindex="3"/>
        <form:errors path="city" cssClass="validationErrorText"/><br/>

        <label for="radius">Within radius of (max.):</label>
        <form:input id="radiusInput" type="number" path="radius"
                    placeholder="e.g. 5" step="5"/>
        km
        <form:errors path="radius" cssClass="validationErrorText"/>
        <br/> <label for="price">Price (max.):</label>
        <form:input id="prizeInput" type="number" path="price"
                    placeholder="e.g. 5" step="50"/>
        CHF
        <form:errors path="price" cssClass="validationErrorText"/><br/>

        <a href="javascript:void(0);" id="advancedSearch" onclick="advancedSearch();" style="color: #ff00ff">Show Advanced Search</a><br>
        <br />
        <hr class="slim">

        <table id="advanced" style="width: 80%; display: none;">
            <tr>
                <td><label for="earliestMoveInDate">Earliest move-in date</label></td>
            </tr>
            <tr>
                <td><form:input type="text" id="field-earliestMoveInDate"
                                path="earliestMoveInDate"/></td>
            </tr>
            <tr>
                <td><label for="latestMoveInDate">Latest move-in date</label></td>
            </tr>
            <tr>
                <td><form:input type="text" id="field-latestMoveInDate"
                                path="latestMoveInDate"/></td>
            </tr>
            <tr>
                <td><form:checkbox id="field-smoker" path="smokers" value="1"/><label>Smoking inside
                    allowed</label></td>
                <td><form:checkbox id="field-animals" path="animals" value="1"/><label>Animals
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

        <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/results';">Search</button>
        <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/profile/alerts';">Subscribe</button>
        <button type="reset">Cancel</button>
    </fieldset>

</form:form>
    </div>


<c:import url="template/footer.jsp"/>