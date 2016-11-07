<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="template/header.jsp"/>

<div class="container">
    <h1>Balance</h1>
    <hr/>

    <div class="row">
        <div class="col-md-8">
            <table class="table table-bordered" id="stat-valueTable">

                <tr>
                    <th>Stats</th>
                    <th>Value</th>
                <tr>
                    <td>Money Earned</td>
                    <td>${moneyEarned}</td>
                </tr>
                    <tr>
                        <td>Money Spent</td>
                        <td>${moneySpent}</td>
                    </tr>
            </table>
        </div>
    </div>

</div>


<c:import url="template/footer.jsp"/><br/>