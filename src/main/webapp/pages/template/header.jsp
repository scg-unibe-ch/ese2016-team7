<%@ page language="java" pageEncoding="UTF-8"
		 contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
		   uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">


	<link rel="stylesheet" type="text/css" media="screen"
		  href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" media="screen"
		  href="/css/main.css">


	<!--<link rel="stylesheet" type="text/css"
		  media="only screen and (max-device-width: 480px)"
		  href="/css/smartphone.css" />-->

	<Title>FlatFindr</Title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script
			src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
	<link rel="stylesheet"
		  href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/smoothness/jquery-ui.css" />

	<script src="/js/unreadMessages.js"></script>


	<style>
		/* ensure that autocomplete lists are not too long and have a scrollbar */
		.ui-autocomplete {
			max-height: 200px;
			overflow-y: auto;
			overflow-x: hidden;
		}
	</style>

</head>

<!-- check if user is logged in -->
<sec:authorize var="loggedIn" url="/profile" />
<sec:authorize var="isAdmin" url="/admin" />

<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand navTitle" href="/">FlatFindr</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="/">Home</a></li>
				<li><a href="/about">About</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<c:url value='/searchAd' />">Search</a></li>
				<c:choose>
					<c:when test="${loggedIn}">
						<script>
							$(document).ready(unreadMessages("header"));
						</script>

						<!-- include user details -->
						<%@include file='/pages/getUserPicture.jsp' %>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
								<img height="25" class="logo" src="<% out.print(filePath); %>" />
								<% out.print(realUser.getFirstName() + " " + realUser.getLastName()); %>
								<span class="caret"></span>



							</a>
							<ul class="dropdown-menu">
								<li><a href="/profile/placeAd">Place an ad</a></li>
								<li><a href="/profile/myRooms">My rooms</a></li>
								<li><a href ="/profile/balance">Balance</a></li>
								<li><a id="messageLink" href="/profile/messages"></a></li>
								<li><a href="/profile/enquiries">Enquiries</a></li>
								<li><a href="/profile/schedule">Schedule</a></li>
								<li><a href="/profile/alerts">Alerts</a></li>
								<c:choose>
									<c:when test="${isAdmin}">
										<li><a href="/admin/insights">Insights</a></li>
									</c:when>
								</c:choose>
								<li>
									<% out.print("<a href=\"/user?id=" + realUser.getId() + "\">Public Profile</a>"); %>
								</li>
								<li><a href="/logout">Logout</a></li>
							</ul>
						</li>
					</c:when>
					<c:otherwise>
						<li><a href="/login">Login</a></li>
					</c:otherwise>
				</c:choose>
				<li><a href="/profile/messages"></a></li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>
<!-- check if user has a profile picture
<header>
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/"><img src="/img/logo.png"></a>
		</div>
			<ul>
				<div class="right">
					<nav>

					</nav>
				</div>
			</ul>
</header>-->

<body>


<!-- will be closed in footer-->

<c:if test="${not empty confirmationMessage }">
	<div class="confirmation-message">
		<img src="/img/check-mark.png" />
		<p>${confirmationMessage }</p>
	</div>
</c:if>