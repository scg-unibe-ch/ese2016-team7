<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp"/>


<script>
    // Validate the email field
    $(document).ready(function () {
        $("#field-email").focusout(function () {
            var text = $(this).val();
            $.post("/signup/doesEmailExist", {email: text}, function (data) {
                if (data) {
                    alert("This username is taken. Please choose another one!");
                    $("#field-email").val("");
                }
            });
        });
    });
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

<!--<pre>
<a href="/">Home</a> &gt; Sign up</pre>-->
<div class="container">
    <div class="well">
        <h1>Sign up</h1>
        <form:form id="signupForm" method="post" modelAttribute="signupForm"
                   action="signup">

            <label for="field-firstName">First Name:</label>
            <form:input path="firstName" id="field-firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="validationErrorText"/>

            <label for="field-lastName">Last Name:</label>
            <form:input path="lastName" id="field-lastName" cssClass="form-control"/>
            <form:errors path="lastName" cssClass="validationErrorText"/>

            <label for="field-password">Password:</label>
            <form:input path="password" id="field-password" type="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="validationErrorText"/>

            <label for="field-email">Email:</label></td>
            <form:input path="email" id="field-email" cssClass="form-control"/>
            <form:errors path="email" cssClass="validationErrorText"/>

            <label for="field-gender">Gender:</label>
            <form:select id="field-gender" path="gender" cssClass="form-control">
                <form:option value="FEMALE" label="Female"/>
                <form:option value="MALE" label="Male"/>
            </form:select>

            <label>Credit Card Number:</label>
            <form:input path="creditCardNumber" id="creditCardNumber" cssClass="form-control"/>
            <form:errors path="creditCardNumber" cssClass="validationErrorText"/>
            <form:checkbox style="display:none" name="hasCreditCard" id="hasCreditCard" path="hasCreditCard"
                           cssClass="checkbox"/>


            <label>Credit Card Expire Month/Year</label>
            <div class="form-group row">
                <div class="col-md-3">
                    <form:input type="number" path="creditCardExpireMonth" cssClass="form-control"/>
                    <form:errors path="creditCardExpireMonth" cssClass="validationErrorText"/>
                </div>
                <div class="col-md-1">
                    <p style="font-size: 20px;">/</p>
                </div>
                <div class="col-md-3">
                    <form:input type="number" path="creditCardExpireYear" cssClass="form-control"/>
                    <form:errors path="creditCardExpireYear" cssClass="validationErrorText"/>
                </div>
            </div>

            <label>Credit Card Security Code</label>
            <form:input type="number" path="securityCode" cssClass="form-control"/>
            <form:errors path="securityCode" cssClass="validationErrorText"/>
            <br/>
            <button type="submit" onclick="determineHasCreditCard()">Sign up</button>

        </form:form>
    </div>
</div>

<c:import url="template/footer.jsp"/>