<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page Title</title>
    <style>
        .error-message {
            font-weight: bold;
            font-size: 18px;
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<jsp:useBean id="errorMessageBean" class="fr.javatheque.beans.ErrorMessageBean" scope="request" />

<c:if test="${not empty errorMessageBean.errorMessage}">
    <div class="error-message">
            ${errorMessageBean.errorMessage}
    </div>
</c:if>

</body>
</html>