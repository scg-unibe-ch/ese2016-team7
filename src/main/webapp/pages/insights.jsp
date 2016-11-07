<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>

<div class="container">
    <div class = "row">
    <h1>Insights</h1>
    <hr/>
        <div class = "col-md-4">

            <div class="table-responsive">
                <table class="table table-striped">

        <tr>
            <th>Stats</th>
            <th>Count</th>
        </tr>
        <tr>
            <td>Subscribed Users</td>
            <td>${usersCount}</td>
        </tr>
        <tr>
            <td>Still active Ads</td>
            <td>${activeAdsCount}</td>
        </tr>
        <tr>
            <td>Already expired Ads</td>
            <td>${expiredAdsCount}</td>
        </tr>
        <tr>
            <td>Total Ads</td>
            <td>${adsCount}</td>
        </tr>
        <tr>
            <td>Money Spent</td>
            <td>${moneySpent}</td>
        </tr>
    </table>
            </div>
            </div>
        <div class = "col-md-4">


            <div class="table-responsive">

                <table class="table table-striped">

        <tr>
            <th>Typology</th>
            <th>Count</th>
        </tr>
        <tr>
            <td>House</td>
            <td>${houseCount}</td>
        </tr>
        <tr>
            <td>Apartment</td>
            <td>${apartmentCount}</td>
        </tr>
        <tr>
            <td>Studio</td>
            <td>${studioCount}</td>
        </tr>
    </table>
                </div>
            </div>


        <div class = "col-md-4">

            <div class="table-responsive">
                <table class="table table-striped">

        <tr>
            <th>Frequency Features Offered</th>
            <th>Count</th>
        </tr>
        <tr>
            <td>Smoking allowed</td>
            <td>${smokersCount}</td>
        </tr>
        <tr>
            <td>Animals Allowed</td>
            <td>${animalsCount}</td>
        </tr>
        <tr>
            <td>Garden</td>
            <td>${gardenCount}</td>
        </tr>
        <tr>
            <td>Balcony</td>
            <td>${balconyCount}</td>
        </tr>
        <tr>
            <td>Cellar</td>
            <td>${cellarCount}</td>
        </tr>
        <tr>
            <td>Furnished</td>
            <td>${furnishedCount}</td>
        </tr>
        <tr>
            <td>Garage</td>
            <td>${garageCount}</td>
        </tr>
    </table>
            </div>
            </div>

        <div class = "col-md-4">

            <div class="table-responsive">
                <table class="table table-striped">

                    <tr>
                        <th>Provisions</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td>${provisionsMade}</td>
                        <td>CHF</td>
                    </tr>

                    <tr>
                        <th>Premium Ad Money</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td>${premiumAdMoney}</td>
                        <td>CHF</td>
                    </tr>
                </table>
            </div>
        </div>


</div>
</div>


<c:import url="template/footer.jsp"/><br/>