<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:import url="template/header.jsp"/>



<style>
    .myButton {
        -moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
        -webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
        box-shadow:inset 0px 1px 0px 0px #ffffff;
        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ffffff), color-stop(1, #f6f6f6));
        background:-moz-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
        background:-webkit-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
        background:-o-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
        background:-ms-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
        background:linear-gradient(to bottom, #ffffff 5%, #f6f6f6 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#f6f6f6',GradientType=0);
        background-color:#ffffff;
        -moz-border-radius:6px;
        -webkit-border-radius:6px;
        border-radius:6px;
        border:1px solid #dcdcdc;
        display:inline-block;
        cursor:pointer;
        color:#666666;
        font-family:Arial;
        font-size:15px;
        font-weight:bold;
        padding:6px 24px;
        text-decoration:none;
        text-shadow:0px 1px 0px #ffffff;
        position:relative;
        transition: .5s ease;
        top: 50%;
        left: 90%;
    }
    .myButton:hover {
        background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #f6f6f6), color-stop(1, #ffffff));
        background:-moz-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
        background:-webkit-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
        background:-o-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
        background:-ms-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
        background:linear-gradient(to bottom, #f6f6f6 5%, #ffffff 100%);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#f6f6f6', endColorstr='#ffffff',GradientType=0);
        background-color:#f6f6f6;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }

</style>


<!--<pre><a href="/">Home</a> &gt; <a href="/searchAd/">Search</a> &gt; Results</pre>-->

<script>
    /*
     * This script takes all the resultAd divs and sorts them by a parameter specified by the user.
     * No arguments need to be passed, since the function simply looks up the dropdown selection.
     */
    function sort_div_attribute() {
        //determine sort modus (by which attribute, asc/desc)
        var sortmode = $('#modus').find(":selected").val();

        //only start the process if a modus has been selected
        if (sortmode.length > 0) {
            var attname;

            //determine which variable we pass to the sort function
            if (sortmode == "price_asc" || sortmode == "price_desc")
                attname = 'data-price';
            else if (sortmode == "moveIn_asc" || sortmode == "moveIn_desc")
                attname = 'data-moveIn';
            else if (sortmode == "timeLeft_asc" || sortmode == "timeLeft_desc")
                attname = 'data-expire';
            // TODO Test if this works inst_desc will show ads with no-instant-price first ... change that
            else if (sortmode == "inst_asc" || sortmode == "inst_desc")
                attname = 'data-inst';
            else
                attname = 'data-age';

            //copying divs into an array which we're going to sort
            var divsbucket = new Array();
            var divslist = $('div.resultAd');
            var divlength = divslist.length;
            for (a = 0; a < divlength; a++) {
                divsbucket[a] = new Array();
                divsbucket[a][0] = divslist[a].getAttribute(attname);
                divsbucket[a][1] = divslist[a];
                divslist[a].remove();
            }

            //sort the array
            divsbucket.sort(function (a, b) {
                if (a[0] == b[0])
                    return 0;
                else if (a[0] > b[0])
                    return 1;
                else
                    return -1;
            });

            //invert sorted array for certain sort options
            if (sortmode == "price_desc" || sortmode == "moveIn_asc"
                    || sortmode == "dateAge_asc" || sortmode == "timeLeft_desc")
                divsbucket.reverse();


            //insert sorted divs into document again
            for (a = 0; a < divlength; a++)
                $("#resultsDiv").append($(divsbucket[a][1]));
        }
    }
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("img").each(function () {
            if ($(this).attr('src') == "") {
                $(this).attr('src', "/img/ad_placeholder.png");
            }
        })
    });
</script>


<style>
    #map {
        height: 550px;
        width: 100%;
    }
</style>

<script>

    var map;
    var bounds;
    var counter;

    function initMap() {


        map = new google.maps.Map(document.getElementById('map'), {});
        bounds = new google.maps.LatLngBounds();
        counter = 0;
    }


    function geocodeAndMark(address, picture, title, id, property, moveInDate, price){

        // format property
        if(property == 'APARTMENT') {
            property = 'Apartment';
        }
        else if(property == 'HOUSE') {
            property = 'House';
        }
        else if(property == 'STUDIO') {
            property = 'Studio';
        }

        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({address : address }, function (results, status) {


                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location,
                    url: "/ad?id=" + id,
                });

            var contentString = '<div>'+
                    '<img src=' + picture + ' width="35%" height="35%" align="left"/>' +
                    '<h2 ><b>'+ title  +'</b></h2>'+
                    '<p >'+ address +'</p>'+
                    '<p >'+ property +'</p>'+
                    '<p > Available from:  '+ moveInDate +'</p>'+
                    '<p > Price:  <b>'+ price +' CHF</b></p>'+
                    '</div>';

            var infowindow = new google.maps.InfoWindow({
                content: contentString,
                maxWidth : 500,
                maxHeight : 500
            });

            marker.addListener('mouseover', function() {
                infowindow.open(map, marker);
            });

            marker.addListener('mouseout', function() {
                infowindow.close();
            });

            marker.addListener('click', function() {
                window.location.href = this.url;
            });



            counter++;
            var location = results[0].geometry.location;


            // If result is one then center there otherwise use bounds
            if(counter == 1) {
                map.setCenter(location);
                map.setZoom(13);
                bounds.extend(location);
            }
            else {
                bounds.extend(location);
                map.fitBounds(bounds);
            }
        });

    }


    jQuery(document).ready(function(){
        jQuery('#showMapList').on('click', function(event) {

            if ( $('#map').is(':visible') ) {
                $("#showMapList").attr('value', 'Show Map');
            }
            else {
                $("#showMapList").attr('value', 'Show List');
            }

            jQuery('#map').toggle('show');
            jQuery('#resultList').toggle('show');


        });
    });
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBu6U6EuiTE7PWfkp9AZlfCMqYNIPj1OPY&callback=initMap">
</script>

<center><div id="map">
    <script>
        $('#map').toggle('show');
    </script>
</div>
</center>
<input class="myButton" type='button' id='showMapList' value='Show Map' align = 'right'>


<div class="container" id="resultList">

    <h1>Search</h1>

    <hr/>
    <div>
        <div class="row">
        <div class="col-md-4">
            <div class="sorter">
                <select id="modus">
                    <option value="">Sort by:</option>
                    <option value="price_asc">Price (ascending)</option>
                    <option value="price_desc">Price (descending)</option>
                    <option value="moveIn_desc">Move-in date (earliest to latest)</option>
                    <option value="moveIn_asc">Move-in date (latest to earliest)</option>
                    <option value="dateAge_asc">Date created (youngest to oldest)</option>
                    <option value="dateAge_desc">Date created (oldest to youngest)</option>
                    <option value="timeLeft_asc">Time Left (ascending)</option>
                    <option value="timeLeft_desc">Time Left (descending)</option>
                    <option value="inst_asc">Instant Buy Price (ascending)</option>
                    <option value="inst_desc">Instant Buy Price (descending)</option>
                </select>

                <button onClick="sort_div_attribute()">Sort</button>
            </div>
        </div>
        <div class="col-md-8">
            <button id="showSearch" onclick="showSearch()">Show Search</button>
        </div>
        </div>
        <div class="row">
        <form:form method="post" modelAttribute="searchForm" action="/results"
                   id="filterForm" autocomplete="off" onsubmit="return isValid();">
            <div>
                <div id="resultsSearchDiv" style="display: none">
                    <h2>Search</h2>

                    <form:form method="post" modelAttribute="searchForm" action="/results"
                               id="searchForm" autocomplete="off">
                        <form:checkbox name="house" id="house" path="house"/><label>House</label>
                        <form:checkbox name="studio" id="studio" path="studio"/><label>Studio</label>
                        <form:checkbox name="apartment" id="apartment" path="apartment"/><label>Apartment</label>


                        <div class="row">
                            <div class="form-group">
                                <div class="col-lg-3">

                                    <label for="city">
                                        <div class="searchText">City / zip code:</div>
                                    </label>

                                </div>
                                <div class="col-lg-4">
                                    <form:input type="text" name="city" id="city" path="city" placeholder="e.g. Bern"
                                                tabindex="3" cssClass="searchText"/>
                                    <form:errors path="city" cssClass="validationErrorText"/><br/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <div class="col-sm-3">
                                    <label for="radius">
                                        <div class="searchText"> Within radius of (max.):</div>
                                    </label>
                                </div>
                                <div class="col-lg-4">
                                    <form:input id="radiusInput" type="number" path="radius" placeholder="e.g. 5" step="5"
                                                cssClass="searchText"/> km
                                    <form:errors path="radius" cssClass="validationErrorText"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <div class="col-lg-3">
                                    <label for="price">
                                        <div class="searchText"> Price (max.):</div>
                                    </label>
                                </div>
                                <div class="col-lg-4">
                                    <form:input id="prizeInput" type="number" path="price" placeholder="e.g. 5" step="50"
                                                cssClass="searchText"/> CHF
                                    <form:errors path="price" cssClass="validationErrorText"/><br/>
                                </div>
                            </div>
                        </div>
                        <br/>
                        <hr class="slim">

                        <table id="advanced" style="width: 80%;">
                            <tr>
                                <td><label for="instantBuyPrice">Instant-Buy-Price lower than</label></td>
                            </tr>
                            <tr>
                                <td>
                                    <form:input type="text" id="field-instantBuyPrice" path="instantBuyPrice"/>
                                    <form:errors path="instantBuyPrice" cssClass="validationErrorText"/>
                                    <script type="text/javascript">
                                        if ($("#field-instantBuyPrice").val() == 0) $("#field-instantBuyPrice").val("");
                                    </script>
                                </td>
                            </tr>
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
                                <td><form:checkbox id="field-dishwasher" path="dishwasher" value="1"/><label>Dishwasher</label>
                                </td>
                            </tr>
                            <tr>
                                <td><form:checkbox id="field-washingMachine" path="washingMachine" value="1"/><label>Washing machine</label>
                                </td>
                            </tr>
                        </table>

                        <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/results';">Search
                        </button>
                        <button type="submit" onClick=";markAllPropertiesIfNoneIsMarked();form.action='/profile/alerts';">
                            Subscribe
                        </button>
                        <button type="reset">Cancel</button>

                    </form:form>
                </div>
            </div>
        </div>
        <div class="paddingTop">
        </form:form>
        <c:choose>
        <c:when test="${empty results}">
        <h2>No results found!</h2>
            <script>
                $('#showMapList').toggle("show");
            </script>
            </c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${empty premium}">
        </div>
    </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <div id="resultsDiv" class="resultsDiv">

            <c:forEach var="ad" items="${premium}">
            <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                            type="date" pattern="dd.MM.yyyy"/>
            <script>
                geocodeAndMark("${ad.zipcode} " + "${ad.city}" + " ${ad.street}",
                        "${ad.pictures[0].filePath}",
                        "${ad.title}",
                        "${ad.id}",
                        "${ad.property}",
                        "${formattedMoveInDate}",
                        "${ad.price}");
            </script>
            <div class="col-md-4">
                <div class="thumbnail thumbnailPremium">
                    <a href="<c:url value='/ad?id=${ad.id}' />">
                        <img src="${ad.pictures[0].filePath}" alt="" class="img-responsive">
                    </a>
                    <div class="caption">
                        <h2>
                            <a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a>
                        </h2>
                        <p>${ad.street}, ${ad.zipcode} ${ad.city}
                            <br/>
                            <i><c:choose>
                                <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                                <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                                <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                            </c:choose></i></p>
                        <p>Available from: ${formattedMoveInDate }</p>
                        <div class="row">
                            <div class="col-md-6">
                                <h3>CHF ${ad.price }</h3>
                            </div>
                            <div class="col-md-6">
                                <h3 style="float: right" id='timeLeft${ad.id}p'>Expire Date: <fmt:formatDate
                                        value="${ad.expireDate}" pattern="dd.MM.yyyy HH:mm:ss"/></h3>
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${ad.instantBuyPrice > 0}">
                                <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                            </c:when>
                            <c:otherwise>
                                <h3>No-Instant-Buy</h3>
                            </c:otherwise>
                        </c:choose>
                        <p>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <strong>Premium</strong>
                        </p>
                    </div>
                </div>
            </div>

            <script>
                var expired = ${ad.expireDate.getTime()};
                var current = new Date();

                if (current > expired) {
                    $('#bidInfo').html("<h2>We are sorry but this auction is over!</h2>");
                } else {
                    var msec = expired - current;

                    var dd = Math.floor(msec / 1000 / 60 / 60 / 24);

                    msec -= dd * 1000 * 60 * 60 * 24;
                    var hh = Math.floor(msec / 1000 / 60 / 60);
                    msec -= hh * 1000 * 60 * 60;
                    var mm = Math.floor(msec / 1000 / 60);
                    msec -= mm * 1000 * 60;
                    var ss = Math.floor(msec / 1000);
                    msec -= ss * 1000;
                    if (dd > 0) {
                        $('#timeLeft${ad.id}p').html("Time Left: " + dd + "days");
                    }
                    else if (hh > 0) {
                        $('#timeLeft${ad.id}p').html("Time Left: " + hh + "Hours");
                    }
                    else if (mm > 0) {
                        $('#timeLeft${ad.id}p').html("Time Left: " + mm + " Minutes");
                    }
                    else {
                        $('#timeLeft${ad.id}p').html("Time Left: " + ss + "seconds");
                    }

                }
            </script>
            </div>

        </c:forEach>
    </c:otherwise>
    </c:choose>
        <c:forEach var="ad" items="${results}">
            <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                            type="date" pattern="dd.MM.yyyy"/>
            <script>

                geocodeAndMark("${ad.zipcode} " + "${ad.city}" + " ${ad.street}",
                        "${ad.pictures[0].filePath}",
                        "${ad.title}",
                        "${ad.id}",
                        "${ad.property}",
                        "${formattedMoveInDate}",
                        "${ad.price}");
            </script>

            <div class="col-md-4 resultAd" data-price="${ad.price}"
                 data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}" data-expire="${ad.expireDate}" data-inst="${ad.instantBuyPrice}">
                <div class="thumbnail <c:choose><c:when test="${ad.premium}">thumbnailPremium</c:when></c:choose>">
                    <a href="<c:url value='/ad?id=${ad.id}' />">
                        <img src="${ad.pictures[0].filePath}" alt="" class="img-responsive">
                    </a>
                    <div class="caption">
                        <h4><a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></h4>
                        <p>${ad.street}, ${ad.zipcode} ${ad.city}
                            <br/><i><c:choose>
                                <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                                <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                                <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                            </c:choose></i></p>

                        <p>Available from: ${formattedMoveInDate }</p>

                        <div class="row">
                            <div class="col-md-6">
                                <h3>CHF ${ad.price }</h3>
                            </div>
                            <div class="col-md-6">
                                <h3 style="float: right" id='timeLeft${ad.id}'>Expire Date: <fmt:formatDate
                                        value="${ad.expireDate}" pattern="dd.MM.yyyy HH:mm:ss"/></h3>
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${ad.instantBuyPrice > 0}">
                                <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                            </c:when>
                            <c:otherwise>
                                <h3>No-Instant-Buy</h3>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${ad.premium}">
                                <p>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <strong>Premium</strong>
                                </p>
                            </c:when>
                        </c:choose>
                        <script>
                            var expired = ${ad.expireDate.getTime()};
                            var current = new Date();

                            if (current > expired) {
                                $('#bidInfo').html("<h2>We are sorry but this auction is over!</h2>");
                            } else {
                                var msec = expired - current;

                                var dd = Math.floor(msec / 1000 / 60 / 60 / 24);

                                msec -= dd * 1000 * 60 * 60 * 24;
                                var hh = Math.floor(msec / 1000 / 60 / 60);
                                msec -= hh * 1000 * 60 * 60;
                                var mm = Math.floor(msec / 1000 / 60);
                                msec -= mm * 1000 * 60;
                                var ss = Math.floor(msec / 1000);
                                msec -= ss * 1000;
                                if (dd > 0) {
                                    $('#timeLeft${ad.id}').html("Time Left: " + dd + "days");
                                }
                                else if (hh > 0) {
                                    $('#timeLeft${ad.id}').html("Time Left: " + hh + "Hours");
                                }
                                else if (mm > 0) {
                                    $('#timeLeft${ad.id}').html("Time Left: " + mm + " Minutes");
                                }
                                else {
                                    $('#timeLeft${ad.id}').html("Time Left: " + ss + "seconds");
                                }

                            }
                        </script>

                    </div>
                </div>
            </div>
        </c:forEach>

        </c:otherwise>
        </c:choose>
    </div>


    <script>
        var valid = true;
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

        function markAllPropertiesIfNoneIsMarked() {
            var house = document.getElementById('house');
            var apartment = document.getElementById('apartment');
            var studio = document.getElementById('studio');
            if (!house.checked && !apartment.checked && !studio.checked) {
                house.checked = true;
                apartment.checked = true;
                studio.checked = true;
            }
            if ($("#field-instantBuyPrice").val() == "") $("#field-instantBuyPrice").val(0);
        }
    </script>

    <script>
        function showSearch() {
            if ($("#resultsSearchDiv").css("display") == "none") {
                $("#resultsSearchDiv").css("display", "");
                $("#showSearch").html("Hide Search");
            } else {
                $("#resultsSearchDiv").css("display", "none");
                $("#showSearch").html("Show Search");
            }
        }
    </script>



</div>


<c:import url="template/footer.jsp"/>