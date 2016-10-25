<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>

<pre><a href="/">Home</a>   &gt;   Search</pre>

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
    function validateType(form) {
        var house = document.getElementById('house');
        var studio = document.getElementById('studio');
        var apartment = document.getElementById('apartment');

        var bothHouseAndStudio = document.getElementById('bothHouseAndStudio');
        var bothHouseAndApartment=document.getElementById('bothApartmentAndHouse');
        var bothApartmentAndStudio = document.getElementById('bothApartmentAndStudio');
        var apartmentHouseAndStudio = document.getElementById('apartmentHouseAndStudio');

        var type = document.getElementById('type');
        var filtered = document.getElementById('filtered');

        if(house.checked && studio.checked && apartment.checked){
            apartmentHouseAndStudio.checked=true;
        }

        else if (house.checked && studio.checked) {
            bothHouseAndStudio.checked = true;
        }

        else if (house.checked && apartment.checked) {
            bothHouseAndApartment.checked = true;
        }
        else if(apartment.checked && studio.checked){
            bothApartmentAndStudio.checked=true;
        }
        else{

        }
        filtered.checked = true;
    }
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
</script>

<h1>Search for an ad</h1>
<hr/>
<form:form method="post" modelAttribute="searchForm" action="/results"
           id="searchForm" autocomplete="off">
    <fieldset>
        <form:checkbox name="house" id="house" path="house"/><label>House</label>
        <form:checkbox name="studio" id="studio" path="studio"/><label>Studio</label>
        <form:checkbox name="apartment" id="apartment" path="apartment  "/><label>Apartment</label>

        <form:checkbox style="display:none" name="bothHouseAndStudio" id="bothHouseAndStudio" path="bothHouseAndStudio"/>
        <form:checkbox style="display:none" name="bothApartmentAndHouse" id="bothApartmentAndHouse" path="bothApartmentAndHouse"/>
        <form:checkbox style="display:none" name="bothApartmentAndStudio" id="bothApartmentAndStudio" path="bothApartmentAndStudio"/>
        <form:checkbox style="display:none" name="apartmentHouseAndStudio" id="apartmentHouseAndStudio" path="apartmentHouseAndStudio"/>

        <form:checkbox style="display:none" name="filtered" id="filtered" path="filtered"/>
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

        <button type="submit" onClick="validateType(this.form);form.action='/results';">Search</button>
        <button type="submit" onClick="validateType(this.form);form.action='/profile/alerts';">Subscribe</button>
        <button type="reset">Cancel</button>
    </fieldset>

</form:form>


<c:import url="template/footer.jsp"/>