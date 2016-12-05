<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:import url="template/header.jsp"/>


<script type="text/javascript">
    $(document).ready(function() {
        $("img").each(function () {
            if($(this).attr('src') == ""){
                $(this).attr('src',"/img/ad_placeholder.png");
            }
        });
    });
</script>


<!--<pre><a href="/">Home</a> &gt; My Rooms</pre>-->

<div class="container">
    <c:choose>
        <c:when test="${empty ownAdvertisements}">
            <div class="page-header"><h2>My Advertisements</h2></div>
            <p>You have not advertised anything yet.</p>
            <br/><br/>
        </c:when>
        <c:otherwise>
            <div class="page-header"><h2>My Advertisements</h2></div>
            <div class="row">
                <c:forEach var="ad" items="${ownAdvertisements}">
                    <div class="col-md-3">
                        <div class="thumbnail <c:choose><c:when test="${ad.premium}">thumbnailPremium</c:when></c:choose>">
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
                                <p>Move-in-date: ${ad.moveInDate}</p>
                                <h4><strong>CHF ${ad.price }</strong></h4>
                                <c:choose>
                                    <c:when test="${ad.instantBuyPrice > 0}">
                                        <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>No-Instant-Buy</h3>
                                    </c:otherwise>
                                </c:choose>
                                <br />
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
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <br/> <br/>
            </div>
        </c:otherwise>
    </c:choose>


    <c:choose>
        <c:when test="${empty bookmarkedAdvertisements}">
            <div class="page-header"><h2>My Bookmarks</h2></div>
            <p>You have not bookmarked anything yet.</p>
            <br/><br/>
        </c:when>
        <c:otherwise>
            <div class="page-header"><h2>My Bookmarks</h2></div>
            <div class="row">
                <c:forEach var="ad" items="${bookmarkedAdvertisements}">
                    <div class="col-md-3">
                        <div class="thumbnail <c:choose><c:when test="${ad.premium}">thumbnailPremium</c:when></c:choose>">
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
                                <p>Move-in-date: ${ad.moveInDate}</p>
                                <h4><strong>CHF ${ad.price }</strong></h4>
                                <c:choose>
                                    <c:when test="${ad.instantBuyPrice > 0}">
                                        <h3>Instant-Buy: CHF ${ad.instantBuyPrice}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>No-Instant-Buy</h3>
                                    </c:otherwise>
                                </c:choose>
                                <br />
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
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <br/> <br/>
            </div>
        </c:otherwise>
    </c:choose>
</div>


<c:import url="template/footer.jsp"/>