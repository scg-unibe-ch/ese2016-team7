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
        <p>The worlds greatest platform to buy and sell real estate. Our auction system ensures that you get the best deals. Buy your dream house today and start living your life.</p>
        <form class="form-inline">
            <input type="text" class="form-control input-lg" placeholder="Enter Area (e.g. Bern)"/>
            <button type="submit" class="btn btn-primary btn-lg">Search</button>
        </form>
    </div>

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
        </c:forEach>

        </div>

    <div class="page-header"><h2>Premium Ads</h2></div>
    <div class="row">
        <c:forEach var="ad" items="${premium}">
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
        </c:forEach>

    </div>


    </div>


<c:import url="template/footer.jsp"/><br/>