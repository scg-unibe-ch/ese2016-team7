<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:import url="template/header.jsp"/>


<script type="text/javascript">
    function equalHeight(group) {
        var smallest = 1000000;
        group.each(function() {
            var thisHeight = $(this).height();
            if(thisHeight < smallest) {
                smallest = thisHeight;
            }
        });
        group.each(function() { $(this).height(smallest); });
    }

    $(document).ready(function() {
        equalHeight($(".thumbnail img"));
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
                        <div class="thumbnail">
                            <a href="<c:url value='/ad?id=${ad.id}' />">
                                <img src="${ad.pictures[0].filePath}" alt="">
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
                                <h4>CHF ${ad.price }</h4>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <br/> <br/>
            </div>
        </c:otherwise>
    </c:choose>


    <c:choose>
        <c:when test="${empty ownAdvertisements}">
            <div class="page-header"><h2>My Bookmarks</h2></div>
            <p>You have not bookmarked anything yet.</p>
            <br/><br/>
        </c:when>
        <c:otherwise>
            <div class="page-header"><h2>My Bookmarks</h2></div>
            <div class="row">
                <c:forEach var="ad" items="${bookmarkedAdvertisements}">
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <a href="<c:url value='/ad?id=${ad.id}' />">
                                <img src="${ad.pictures[0].filePath}" alt="">
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
                                <h4>CHF ${ad.price }</h4>
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