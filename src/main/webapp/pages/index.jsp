<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:import url="template/header.jsp"/>
<sec:authorize var="loggedIn" url="/profile" />

<script type="text/javascript">
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
    });
    function isValid() {
        if(!valid && !injected){
            $("#city").after("<span id=\"city.errors\" class=\"validationErrorText\">Please pick a city from the list</span>");
            injected = true;
        }
        return valid
    }
</script>
<script type="text/javascript">

</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("img").each(function () {
            if($(this).attr('src') == ""){
                $(this).attr('src',"/img/ad_placeholder.png");
            }
        })
    });
</script>

<!-- Main jumbotron for a primary marketing message or call to action -->

<div class="container">
    <div class="jumbotron">
        <h1>Welcome to Flatfindr</h1>
        <p>The worlds greatest platform to buy and sell real estate. Our auction system ensures that you get the best
            deals. Buy your dream house today and start living your life.</p>
        <form class="form-inline" method="post" action="/quicksearch" onsubmit="return isValid();">
            <input pattern="^[0-9]{4} - [-\w\s\u00C0-\u00FF]*" type="text" id="city" name="city"
                   class="form-control input-lg"
                   placeholder="Enter Area (e.g. Bern)"/>
            <button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-search"></span>
            </button>
        </form>
    </div>


    <div class="page-header"><h2>Newest Ads</h2></div>
    <c:choose>
        <c:when test="${empty newest && empty premium}">
            <h2>No ads placed yet</h2>
        </c:when>
        <c:otherwise>
            <div class="row">
                <c:forEach var="ad" items="${premium}">
                    <div class="col-md-3">
                        <div class="thumbnail thumbnailPremium">
                            <a href="<c:url value='/ad?id=${ad.id}' />">
                                <img style="height: 196px;" src="${ad.pictures[0].filePath}" alt="">
                            </a>
                            <div class="caption">
                                <h4><a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></h4>
                                <p>${ad.street}, ${ad.zipcode} ${ad.city}
                                    <br/><i><c:choose>
                                        <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                                        <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                                        <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                                    </c:choose></i></p>
                                <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                                type="date" pattern="dd.MM.yyyy"/>

                                <p>Available from: ${formattedMoveInDate }</p>
                                <h4><strong>CHF ${ad.price }</strong></h4>
                                <c:choose>
                                    <c:when test="${ad.instantBuyPrice > 0}">
                                        <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>No-Instant-Buy</h3>
                                    </c:otherwise>
                                </c:choose>
                                <br/>
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
                </c:forEach>
                <c:forEach var="ad" items="${newest}">
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <a href="<c:url value='/ad?id=${ad.id}' />">
                                <img style="height: 196px;" src="${ad.pictures[0].filePath}" alt="">
                            </a>
                            <div class="caption">
                                <h4><a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></h4>
                                <p>${ad.street}, ${ad.zipcode} ${ad.city}
                                    <br/><i><c:choose>
                                        <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                                        <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                                        <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                                    </c:choose></i></p>
                                <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                                type="date" pattern="dd.MM.yyyy"/>

                                <p>Available from: ${formattedMoveInDate }</p>
                                <h4><strong>CHF ${ad.price }</strong></h4>
                                <c:choose>
                                    <c:when test="${ad.instantBuyPrice > 0}">
                                        <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>No-Instant-Buy</h3>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${loggedIn && !empty bookmarks}">
            <div class="page-header"><h2>My Bookmarks</h2></div>
            <div class="row">
                <c:forEach var="ad" items="${bookmarks}">
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <a href="<c:url value='/ad?id=${ad.id}' />">
                                <img style="height: 196px;" src="${ad.pictures[0].filePath}" alt="">
                            </a>
                            <div class="caption">
                                <h4><a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></h4>
                                <p>${ad.street}, ${ad.zipcode} ${ad.city}
                                    <br/><i><c:choose>
                                        <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                                        <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                                        <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                                    </c:choose></i></p>
                                <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                                type="date" pattern="dd.MM.yyyy"/>

                                <p>Available from: ${formattedMoveInDate }</p>
                                <h4><strong>CHF ${ad.price }</strong></h4>
                                <c:choose>
                                    <c:when test="${ad.instantBuyPrice > 0}">
                                        <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>No-Instant-Buy</h3>
                                    </c:otherwise>
                                </c:choose>
                                <br/>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>
</div>


<c:import url="template/footer.jsp"/>