<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>



<!-- check if user is logged in -->
<sec:authorize var="loggedIn" url="/profile"/>

<c:import url="template/header.jsp"/>
<!--<pre>
<a href="/">Home</a> &gt; Login</pre>-->

<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="586882195932-m1navf449n5otkiklfkitfl6j3aov0t1.apps.googleusercontent.com">


<div class="container">
    <div class="well">
        <h1>Login</h1>
        <c:choose>
            <c:when test="${loggedIn}">
                <p>You are already logged in!</p>
            </c:when>
            <c:otherwise>
                <c:if test="${!empty param.error}">
                    <p>Incorrect email or password. Please retry using correct email
                        and password.</p>
                    <br/>
                </c:if>

                <form class="form-inline" id="form" method="post" action="/j_spring_security_check">
                    <div class="form-group">
                        <label for="field-email">Email:</label>
                        <input name="username" id="field-email" class="form-control"/>
                        <label for="field-password">Password:</label>
                        <input name="password" id="field-password" class="form-control" type="password"/>
                        <button type="submit">Login</button>
                    </div>
                    <br/>
                    <br/>


                    Login with Google:
                    <div>
                        <div class="g-signin2" data-onsuccess="onSignIn"></div>
                    </div>
                </form>
                <br/>
                <h2>Test users</h2>

                <ul class="test-users">
                    <li>Email: <i>ese@unibe.ch</i>, password: <i>ese</i></li>
                    <li>Email: <i>jane@doe.com</i>, password: <i>password</i></li>
                    <li>Email: <i>user@bern.com</i>, password: <i>password</i></li>
                    <li>Email: <i>oprah@winfrey.com</i>, password: <i>password</i></li>
                </ul>
                <br/>

                <h2>Admin test user</h2>
                <ul class="test-users">
                    <li>Email: <i>system</i>, password: <i>1234</i></li>
                </ul>
                <br/>

                <h2>Roommates for AdBern</h2>
                <ul class="test-users">
                    <li>Email: <i>hans@unibe.ch</i>, password: <i>password</i></li>
                    <li>Email: <i>mathilda@unibe.ch</i>, password: <i>password</i></li>
                </ul>
                <br/>

                Or <a class="link" href="<c:url value="/signup" />">sign up</a> as a new user.

            </c:otherwise>
        </c:choose>
    </div>
</div>


<script>
    function onSignIn(googleUser) {
        var profile = googleUser.getBasicProfile();
        var name = profile.getName();
        var email = profile.getEmail();

        $.post("/googleSignup", {name: name, email: email}, function () {
            // alert("You bid: " + amount + " CHF");
            //location.reload();
            window.location = "/";

        });
        //$.post(/j_spring_security_check)
        //console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        // console.log('Name: ' + profile.getName());
        //console.log('Image URL: ' + profile.getImageUrl());
        // console.log('Email: ' + profile.getEmail());
    }
</script>


<c:import url="template/footer.jsp"/>