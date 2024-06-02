<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix - Library</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Arial', sans-serif;
            background-color: #273c75;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
            overflow-y: auto;
            overflow-x: hidden;
        }

        body::-webkit-scrollbar {
            display: none;
        }

        h1 {
            margin: 20px 0;
        }

        .error-message,
        .success-message {
            font-weight: bold;
            font-size: 18px;
            margin: 10px 0;
        }

        .error-message {
            color: #eb3b5a;
        }

        .success-message {
            color: green;
        }

        #logout,
        #login,
        #add-film,
        #search-film,
        button {
            margin: 10px;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        #search-film {
            background-color: #2ecc71;
            color: #fff;
        }

        #logout {
            background-color: #e74c3c;
            color: #fff;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
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

        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            padding: 20px;
            background-color: #192a56;
        }

        .header-container h1 {
            margin: 0;
            text-align: center;
        }

        .film-card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
        }

        .film-card {
            background-color: #2c3e50;
            padding: 20px;
            margin: 10px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease;
            width: 300px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .film-card:hover {
            background-color: #34495e;
        }

        .film-card img {
            width: 100%;
            border-radius: 4px;
            margin-bottom: 10px;
        }

        .film-details {
            color: #fff;
            width: 100%;
            text-align: center;
        }

        .film-details p {
            margin: 0;
        }

        form.inline-buttons {
            display: flex;
            justify-content: space-around;
            width: 100%;
            margin-top: 10px;
        }

        form.inline-buttons button {
            background-color: #3498db;
            color: #fff;
            border: none;
            border-radius: 4px;
            padding: 8px;
            flex-grow: 1;
        }

        .search-container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
        }

        #search-form {
            display: flex;
            width: 60%;
        }

        #search-bar,
        #search-button {
            padding: 10px;
            border: none;
            border-radius: 10px;
        }

        #search-bar {
            flex-grow: 1;
        }

        #search-button {
            background-color: #3498db;
            color: #fff;
            cursor: pointer;
        }

        #search-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<div class="error-message">
    <c:if test="${not empty errorMessageBean.errorMessage}">
        <p>${errorMessageBean.errorMessage}</p>
    </c:if>
</div>

<div class="success-message">
    <c:if test="${not empty successMessageBean.successMessage}">
        <p>${successMessageBean.successMessage}</p>
    </c:if>
</div>

<c:if test="${not empty sessionScope.userID}">
    <div class="header-container">
        <button id="search-film" onclick='redirectToSearchFilm()'>Search film to add</button>
        <h1>.NETFlix</h1>
        <button id="logout" onclick='redirectToLogout()'>Logout</button>
    </div>

    <div class="search-container">
        <form id="search-form" action="${pageContext.request.contextPath}/library" method="get">
            <input type="text" id="search-bar" name="search" placeholder="Search..." value="${param.search != 'all' ? param.search : ''}">
            <button type="submit" id="search-button">Search</button>
        </form>
    </div>

    <div class="film-card-container">
        <c:choose>
            <c:when test="${not empty films}">
                <c:forEach var="film" items="${films}">
                    <div class="film-card">
                        <img src="${film.poster}" alt="${film.title}">
                        <div class="film-details">
                            <p>${film.title}</p>
                            <p><em>${film.year}</em></p>
                            <p><strong>${film.support}</strong> (${film.lang})</p>
                            <form action="${pageContext.request.contextPath}/show" method="get" class="inline-buttons">
                                <input type="hidden" name="id" value="${film.id}">
                                <button type="submit" style="background-color: #6ab04c;">Consult</button>
                            </form>
                            <button type="submit" style="background-color: #eb3b5a;" onclick="deleteFilm('${film.id}', '${param.search}')">Delete</button>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No films found in your library.</p>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>

<c:if test="${empty sessionScope.userID}">
    <button id="login" onclick='redirectToLogin()'>Login</button>
</c:if>

<script>
    function redirectToLogout() {
        window.location.href = "${pageContext.request.contextPath}/logout";
    }

    function redirectToLogin() {
        window.location.href = "${pageContext.request.contextPath}/login";
    }

    function redirectToSearchFilm() {
        window.location.href = "${pageContext.request.contextPath}/search";
    }

    function deleteFilm(id, search) {
        let result = confirm("Are you sure? This will permanently delete this film!");
        if (result) {
            fetch("${pageContext.request.contextPath}/" + id + "/delete", {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    'tmdb_id': id,
                }),
            })
                .then(function(response){
                    console.log("Success", response);
                    window.location.href = '${pageContext.request.contextPath}/library?search=' + search;
                })
                .catch(function(){
                    console.log("Error", response);
                })
        }
    }

    document.getElementById('search-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const searchBar = document.getElementById('search-bar');
        const searchValue = searchBar.value.trim();

        if (searchValue !== "") {
            window.location.href = '${pageContext.request.contextPath}/library?search=' + encodeURIComponent(searchValue);
        } else {
            window.location.href = '${pageContext.request.contextPath}/library?search=all';
        }
    });
</script>
</body>
</html>