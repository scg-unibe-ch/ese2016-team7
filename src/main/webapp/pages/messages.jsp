<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>
<!--<pre><a href="/">Home</a> &gt; Messages</pre>-->

<!-- format the dates -->
<fmt:formatDate value="${messages[0].dateSent}" var="formattedDateSent"
                type="date" pattern="HH:mm, dd.MM.yyyy"/>

<script src="/js/unreadMessages.js"></script>
<script src="/js/messages.js"></script>

<script>
    $(document).ready(unreadMessages("messages"));
</script>


<div class="container">
    <h1>Messages</h1>
    <hr/>
    <div id="folders">
        <h2 id="inbox">Inbox</h2>
        <h2 id="newMessage">New</h2>
        <h2 id="sent">Sent</h2>
    </div>
    <div id="messageList">
        <table class="styledTable">
            <tr>
                <th id="subjectColumn">Subject</th>
                <th>Sender</th>
                <th>Recipient</th>
                <th>Date sent</th>
            </tr>
            <c:forEach items="${messages }" var="message">
                <fmt:formatDate value="${message.dateSent}"
                                var="singleFormattedDateSent" type="date"
                                pattern="HH:mm, dd.MM.yyyy"/>

                <tr data-id="${message.id}" class="${message.state}">
                    <td>${message.subject }</td>
                    <td>${message.sender.email}</td>
                    <td>${message.recipient.email }</td>
                    <td>${singleFormattedDateSent}</td>
                </tr>
            </c:forEach>
        </table>
        <hr/>
        <div id="messageDetail">
            <h2>${messages[0].subject }</h2>
            <h3>
                <b>To: </b>${messages[0].recipient.email }
            </h3>
            <h3>
                <b>From: </b> ${messages[0].sender.email }
            </h3>
            <h3>
                <b>Date sent:</b> ${formattedDateSent}
            </h3>
            <br/>
            <p>${messages[0].text }</p>
        </div>
    </div>
</div>

<div id="content">
    <div id="msgDiv">
        <form class="msgForm">
            <h2>Contact someone</h2>
            <label>Recepient: <span>*</span></label>
            <input class="msgInput" type="text" id="receiverEmail" placeholder="Recipient"/>
            <br><br>
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
</div>

<c:import url="getMessageForm.jsp"/>
<c:import url="template/footer.jsp"/>