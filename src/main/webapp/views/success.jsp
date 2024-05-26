<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page Title</title>
    <style>
        .success-message {
            font-weight: bold;
            font-size: 18px;
            color: green;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<jsp:useBean id="successMessageBean" class="fr.javatheque.beans.SuccessMessageBean" scope="request" />

<c:if test="${not empty successMessageBean.successMessage}">
    <div class="success-message">
            ${successMessageBean.successMessage}
    </div>
</c:if>

</body>
</html>