<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:import url="template/header.jsp"/>

<script src="/js/editProfile.js"></script>

<div class="container-fluid">

    <script>
        $(document).ready(function () {
            $("#about-me").val("${currentUser.aboutMe}")
        });
    </script>
    <script>
        $(window).load(function(){
            var creditCardNumber = document.getElementById("creditCardNumber");
            var hasCreditCard = document.getElementById("hasCreditCard");
            if (${hasCreditCard}) {
                $("#creditCard").show();
                hasCreditCard.checked = true;
            } else {
                $("#creditCard").hide();
                hasCreditCard.checked = false;
            }
            if (creditCardNumber == null || creditCardNumber == "")
                hasCreditCard.checked = false;
            else {
                hasCreditCard.checked = true;
            }
            $("#about-me").val("${currentUser.aboutMe}")
        })
    </script>

    <script>
        function determineHasCreditCard() {
            var creditCardNumber = document.getElementById("creditCardNumber");
            var hasCreditCard = document.getElementById("hasCreditCard");
            if (creditCardNumber == null || creditCardNumber == "")
                hasCreditCard.checked = false;
            else {
                hasCreditCard.checked = true;
            }
        }
    </script>

    <!--<pre><a href="/">Home</a>   &gt;   <a href="/user?id=${currentUser.id}">Public Profile</a>   &gt;   Edit profile</pre>-->

    <div class="container">

        <h1>Edit your Profile</h1>
        <hr/>

        <!-- check if user is logged in -->
        <sec:authorize var="loggedIn" url="/profile"/>


        <c:choose>
            <c:when test="${loggedIn}">
                <a id="profile_picture_editPage"> <c:import
                        url="/pages/getUserPicture.jsp"/>
                </a>
            </c:when>
            <c:otherwise>
                <a href="/login">Login</a>
            </c:otherwise>
        </c:choose>

        <form:form method="post" modelAttribute="editProfileForm"
                   action="/profile/editProfile" id="editProfileForm" autocomplete="off"
                   enctype="multipart/form-data">

            <table class="editProfileTable">
                <tr>
                    <td class="spacingTable"><label for="user-name">Username:</label><a>&emsp;</a>
                        <form:input id="user-name" path="username" value="${currentUser.username}"/></td>

                </tr>
                <tr>
                    <td class="spacingTable"><label for="first-name">First name:</label><a>&emsp;</a>
                        <form:input id="first-name" path="firstName" value="${currentUser.firstName}"/></td>
                </tr>
                <tr>
                    <td class="spacingTable"><label for="last-name">Last name:</label><a>&emsp;</a>
                        <form:input id="last-name" path="lastName" value="${currentUser.lastName}"/></td>
                </tr>
                <tr>
                    <td class="spacingTable"><label for="password">Password:</label><a>&emsp;&thinsp;</a>
                        <form:input type="password" id="password" path="password" value="${currentUser.password}"/></td>
                </tr>
            </table>
            <div id="creditCard">
            <table>
                <tr>
                    <td class="spacingTable"><label for="creditCardNumber">Credit Card
                        Number:</label><a>&emsp;&thinsp;</a>
                        <form:input id="creditCardNumber" path="creditCardNumber" value="${currentUser.creditCardNumber}" cssClass="form-control"/>
                        <form:errors path="creditCardNumber" cssClass="validationErrorText"/>
                        <form:checkbox style="display:none" name="hasCreditCard" id="hasCreditCard" path="hasCreditCard"
                                       cssClass="checkbox"/>
                    </td>
                </tr>
            </table>
            <table class="editProfileNumberTable">
                <tr>
                    <td class="spacingTable"><label>Credit Card Expire Month/Year:</label>
                        <form:input type="number" id="creditCardExpireMonth" path="creditCardExpireMonth"
                                    value="${currentUser.creditCardExpireMonth}"/>
                        <form:errors path="creditCardExpireMonth" cssClass="validationErrorText"/>
                        <form:input type="number" id="creditCardExpireYear" path="creditCardExpireYear"
                                    value="${currentUser.creditCardExpireYear}"/>
                        <form:errors path="creditCardExpireYear" cssClass="validationErrorText"/></td>
                </tr>
                <tr>
                    <td class="spacingTable"><label>Credit Card Security Code:</label>
                        <form:input id="securityCode" path="securityCode"
                                    value="${currentUser.securityCode}" cssClass="form-control"/>
                        <form:errors path="securityCode" cssClass="validationErrorText"/></td>
                </tr>
                </table>
            </div>

            <table>
                <c:choose>
                    <c:when test="${hasCreditCard}">
                        <tr>
                            <td>
                                <div class="deleteCreditCard">
                                    <button type="button"  id="deleteCreditCardButton" data-user-id="${currentUser.id }">Delete credit card</button>
                                </div>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>
                                <div class="addCreditCard">
                                    <button type="button" id="addCreditCardButton" data-user-id="${currentUser.id }">Add credit card</button>
                                </div>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>

                <tr>
                    <td class="spacingTable"><label for="about-me">About me:</label><a>&emsp;&thinsp;</a><br>
                        <form:textarea id="about-me" path="aboutMe" rows="10" cols="100"
                                       value="${currentUser.aboutMe}"/></td>
                </tr>
            </table>

            <div>
                <button type="submit" onclick="determineHasCreditCard()">Update</button>
            </div>

        </form:form>
    </div>
</div>


<c:import url="template/footer.jsp"/>

