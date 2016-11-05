<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>


<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container-fluid">
        </br>
        <h2>Our Statement</h2>
        <p>Statement about how cool we are blablabla and a quick search below please. </p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Search &raquo;</a></p>
    </div>
</div>

<div class="container-fluid">
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
                    <a href="<c:url value='/ad?id=${ad.id}' />"><img
                            src="${ad.pictures[0].filePath}"/></a>
                    <h2>
                        <a class="link" href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a>
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
    <div id="resultsDiv" class="resultsDiv">
        <c:forEach var="ad" items="${newest}">
            <div class="resultAd">
                <div class="resultLeft">

                    <a href="<c:url value='/ad?id=${ad.id}' />"><img
                            src="${ad.pictures[0].filePath}" class="img-rounded"/></a>
                    <h2>
                        <a class="link" href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a>
                    </h2>
                    <p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
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

                    <fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
                                    type="date" pattern="dd.MM.yyyy"/>

                    <p>Move-in date: ${formattedMoveInDate }</p>
                </div>
            </div>
        </c:forEach>
    </div>
    </c:otherwise>
    </c:choose>
    </div>
    </div>






<c:import url="template/footer.jsp"/><br/>