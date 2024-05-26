<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #273c75;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            height: 100vh;
        }

        h1 {
            text-align: center;
            color: #f4f4f4;
            margin-top: 0;
            font-size: 30px;
        }

        button {
            padding: 10px 20px;
            font-size: 20px;
            cursor: pointer;
            background-color: #be2edd;
            color: #fff;
            border: none;
            border-radius: 5px;
            margin: 10px;
        }

        button:hover {
            background-color: #e056fd;
        }

        .success-message {
            font-weight: bold;
            font-size: 18px;
            color: green;
            margin-bottom: 10px;
        }

        .error-message {
            font-weight: bold;
            font-size: 18px;
            color: red;
            margin-bottom: 10px;
        }

        p.welcome {
            margin: 10px 0;
            color: #fff;
            font-size: 20px;
        }
    </style>
</head>
<body>
<h1>.NETFlix</h1>
<div>
    <%@ include file="success.jsp" %>
    <%@ include file="error.jsp" %>
</div>

<jsp:useBean id="userBean" class="fr.javatheque.beans.UserBean" scope="request" />

<c:if test="${not empty userBean.userId}">
    <p class="welcome">Welcome <span style="color: #F97F51;"><strong>${userBean.lastname} ${userBean.firstname}</strong></span>!</p>
    <form action="/library" method="GET">
        <input type="hidden" name="search" value="all">
        <button type="submit" id="library">Go to Library</button>
    </form>
    <button id="logout" style="background-color: #eb3b5a;" onclick="redirectToLogout()">Logout</button>
</c:if>
<c:if test="${empty userBean.userId}">
    <button id="loginBtn" onclick="redirectToLogin()">Login</button>
    <button id="registerBtn" onclick="redirectToRegister()">Register</button>
</c:if>

<script>
    function redirectToLogin() {
        window.location.href = "${pageContext.request.contextPath}/login";
    }

    function redirectToRegister() {
        window.location.href = "${pageContext.request.contextPath}/register";
    }

    function redirectToLogout() {
        window.location.href = "${pageContext.request.contextPath}/logout";
    }
</script>
</body>
</html>