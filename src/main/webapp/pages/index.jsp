<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>

<!-- Main jumbotron for a primary marketing message or call to action -->

<div class="container">
    <div class="jumbotron">
        <h1>Welcome to Flatfindr</h1>
        <p>Start searching in your Neighborhood</p>
        <form class="form-inline" method="post" action="/quicksearch">
            <input type="text" id="city" name="city" class="form-control" placeholder="Search for your location"/>
            <button type="submit" class="btn"><span class="glyphicon glyphicon-search"></span></button>
        <p>The worlds greatest platform to buy and sell real estate. Our auction system ensures that you get the best deals. Buy your dream house today and start living your life.</p>
        <form class="form-inline">
            <input type="text" class="form-control input-lg" placeholder="Enter Area (e.g. Bern)"/>
            <button type="submit" class="btn btn-primary btn-lg">Search</button>
        </form>
    </div>
    <!--
<div class="row">
<h2>Heading</h2>
<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>

<c:choose>
    <c:when test="${empty newest}">
        <h2>No ads placed yet</h2>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${empty premium}">
                <h2>No premium Ads found</h2>
            </c:when>
            <c:otherwise>
                <div id="premiumResultsDiv" class="resultsDiv">
                <h2>Premium ads:</h2>
                <c:forEach var="ad" items="${premium}">
                    <div class="resultAd">
                    <div class="resultLeft">
                    <a href="<c:url value='/ad?id=${ad.id}'/>"><img
                    src="${ad.pictures[0].filePath}"/></a>
                    <h2>
                    <a class="link" href="<c:url value='/ad?id=${ad.id}'/>">${ad.title}</a>
                    </h2>
                    <p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
                    <br/>
                    <p>
                    <i><c:choose>
                    <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                    <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                    <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                </c:choose></i>
                    </p>
                    </div>
                    <div class="resultRight">
                    <h2>CHF ${ad.price }</h2>
                    <br/> <br/>

                    <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                    type="date" pattern="dd.MM.yyyy"/>

                    <p>Move-in date: ${formattedMoveInDate }</p>
                    </div>
                    </div>
                </c:forEach>
                </div>

            </c:otherwise>
        </c:choose>
        -->

    <div class="page-header"><h2>Newest Ads</h2></div>
    <div class="row">
        <c:forEach var="ad" items="${newest}">
            <div class="col-md-3">
                <div class="thumbnail">
                    <a href="<c:url value='/ad?id=${ad.id}' />">
                    <img src="${ad.pictures[0].filePath}" alt="">
                        </a>
                    <div class="caption">
                        <h4><a href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></h4>
                        <p>${ad.street}, ${ad.zipcode} ${ad.city}
                            <br /><i><c:choose>
                            <c:when test="${ad.property == 'HOUSE'}">House</c:when>
                            <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
                            <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
                        </c:choose></i></p>
                        <p>

                        </p>
                        <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                        type="date" pattern="dd.MM.yyyy"/>

                        <p>Available from: ${formattedMoveInDate }</p>
                        <h4>CHF ${ad.price }</h4>
                    </div>
                </div>
            </div>

            <!--
            <div class="col-sm-3 col-md-3 col-lg-3">
            <a href="<c:url value='/ad?id=${ad.id}'/>">
            <img width="250" src="${ad.pictures[0].filePath}" class="img-responsive img-rounded"/>
            </a>
            <h2>
            <a class="link" href="<c:url value='/ad?id=${ad.id}'/>">${ad.title}</a>
            </h2>
            <p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
            <p>
            <i><c:choose>
            <c:when test="${ad.property == 'HOUSE'}">House</c:when>
            <c:when test="${ad.property == 'APARTMENT'}">Apartment</c:when>
            <c:when test="${ad.property == 'STUDIO'}">Studio</c:when>
        </c:choose></i>
            </p>

            <h2>CHF ${ad.price }</h2>

            <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                            type="date" pattern="dd.MM.yyyy"/>

            <p>Move-in date: ${formattedMoveInDate }</p>
            </div>-->
        </c:forEach>
    </div>
    </c:otherwise>
    </c:choose>
</div>
</div>


<c:import url="template/footer.jsp"/><br/>