<%@page import="ch.unibe.ese.team1.model.Ad" %>
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- check if user is logged in -->
<sec:authorize var="loggedIn" url="/profile" />

<c:import url="template/header.jsp"/>

<!--<pre><a href="/">Home</a> &gt; <a href="/profile/myRooms">My Rooms</a> &gt; Ad Description</pre>-->

<script src="/js/image_slider.js"></script>
<script src="/js/adDescription.js"></script>

<div id="content" class="container"> <!-- this id needs to be here for the javascript to work -->



    <hr/>


    <div class="row">
        <h1 align="center">Whoops, we couldn't find this ad.</h1>

    </div>

    <hr class="clearBoth"/>


    <div id="msgDiv">
        <form class="msgForm">
            <h2>Contact the advertiser</h2>
            <br>
            <br>
            <label>Subject: <span>*</span></label>
            <input class="msgInput" type="text" id="msgSubject" placeholder="Subject"/>
            <br><br>
            <label>Message: </label>
            <textarea id="msgTextarea" placeholder="Message"></textarea>
            <br/>
            <button type="button" id="messageSend">Send</button>
            <button type="button" id="messageCancel">Cancel</button>
        </form>
    </div>

    <div id="confirmationDialog">
        <form>
            <p>Send enquiry to advertiser?</p>
            <button type="button" id="confirmationDialogSend">Send</button>
            <button type="button" id="confirmationDialogCancel">Cancel</button>
        </form>
    </div>
</div>
</div>



<c:import url="template/footer.jsp"/>