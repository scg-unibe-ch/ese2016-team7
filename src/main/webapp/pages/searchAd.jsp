<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>


<!--<pre><a href="/">Home</a> &gt; Search</pre>-->


<script>
    var valid = false;
    var injected = false;

    $(document).ready(function () {
        $("#city").autocomplete({
            minLength: 2
        });
        $("#city").autocomplete({
            source: <c:import url="getzipcodes.jsp" />,
            select: function (e) {
                valid = true;
            },
            response: function (event, ui) {
                valid = false;
                $.each(ui.content, function (key,value) {
                    for(k in value){
                        if(value[k] == $("#city").val()){
                            valid = true;
                        }
                    }
                });
            }
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
    function isValid() {
        if(!valid && !injected){
            $("#city").after("<span id=\"city.errors\" class=\"validationErrorText\">Please pick a city from the list</span>");
            injected = true;
        }
        return valid
    }
</script>


<script>
    function advancedSearch() {
        if ($("#advanced").css("display") == "none") {
            $("#advanced").css("display", "");
            $("#advancedSearch").html("Hide Advanced Search");
        } else {
            $("#advanced").css("display", "none");
            $("#advancedSearch").html("Show Advanced Search");
        }
    }

    function markAllPropertiesIfNoneIsMarked() {
        var house = document.getElementById('house');
        var apartment = document.getElementById('apartment');
        var studio = document.getElementById('studio');
        if (!house.checked && !apartment.checked && !studio.checked) {
            house.checked = true;
            apartment.checked = true;
            studio.checked = true;
        }
        if($("#field-instantBuyPrice").val() == "") $("#field-instantBuyPrice").val(0);
    }
</script>

<div class="container">

    <h1>Search for an ad</h1>
    <hr/>
    <form:form method="post" modelAttribute="searchForm" action="/results"
               id="searchForm" autocomplete="off" onsubmit="return isValid();">
        <div class="check-box">
        <fieldset>
            <div class="row">
            <form:checkbox name="house" id="house" path="house" cssClass="inputCheckbox"/><label class="searchText"> House</label>
            <form:checkbox name="studio" id="studio" path="studio" cssClass="inputCheckbox"/><label class="searchText"> Studio</label>
            <form:checkbox name="apartment" id="apartment" path="apartment" cssClass="inputCheckbox"/><label class="searchText"> Apartment</label>
            </div>
        </div>

                <div class ="row">
                    <div class="form-group">
                        <div class="col-lg-3">

            <label for="city"><div class="searchText">City / zip code:</div></label>

                        </div>
                        <div class="col-lg-4">
            <form:input type="text" name="city" id="city" path="city" placeholder="e.g. Bern" tabindex="3" cssClass="searchText"/>
            <form:errors path="city" cssClass="validationErrorText"/><br/>
                        </div>
            </div>
                    </div>
        <br/>
        <div class ="row">
             <div class="form-group">
                 <div class="col-sm-3">
            <label for="radius"><div class="searchText"> Within radius of (max.):</div></label>
                 </div>
                 <div class="col-lg-4">
            <form:input id="radiusInput" type="number" path="radius" placeholder="e.g. 5" step="5" cssClass="searchText"/> km
            <form:errors path="radius" cssClass="validationErrorText"/>
                 </div>
             </div>
        </div>
        <br/>
        <div class="row">
            <div class="form-group">
                <div class="col-lg-3">
            <label for="price"><div class="searchText"> Price (max.):</div></label>
                </div>
                <div class="col-lg-4">
            <form:input id="prizeInput" type="number" path="price" placeholder="e.g. 5" step="50" cssClass="searchText"/> CHF
            <form:errors path="price" cssClass="validationErrorText"/><br/>
                </div>
            </div>
            <br/>
            <br/>
        </div>
            <a href="javascript:void(0);" id="advancedSearch" onclick="advancedSearch();" style="color: #0000ff" class="searchText">Show Advanced Search</a>
            <br />
            <hr class="slim">
            <table id="advanced" style="width: 80%; display: none;" class="advanced">
                <tr>
                    <td><label for="instantBuyPrice">Instant-Buy-Price lower than</label></td>
                </tr>
                <tr>
                    <td>
                        <form:input type="text" id="field-instantBuyPrice" path="instantBuyPrice"/>
                        <form:errors path="instantBuyPrice" cssClass="validationErrorText"/>
                        <script type="text/javascript">
                            if($("#field-instantBuyPrice").val() == 0) $("#field-instantBuyPrice").val("");
                        </script>
                    </td>
                </tr>
                <tr>
                    <td><label for="earliestMoveInDate" class="searchText">Earliest move-in date</label></td>
                </tr>
                <tr>
                    <td><form:input type="text" id="field-earliestMoveInDate" path="earliestMoveInDate"/></td>
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

            <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/results';">Search
            </button>
            <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/profile/alerts';">
                Subscribe
            </button>
            <button type="reset">Cancel</button>
        </fieldset>

    </form:form>
</div>


<c:import url="template/footer.jsp"/>