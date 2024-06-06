<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${film.title} - .NETFlix</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #273c75;
            color: #fff;
        }

        header, footer {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 1em 0;
        }

        h1 {
            color: #fff;
            text-decoration: none;
            text-align: center;
        }

        .film-details {
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            color: #000000;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            text-align: center;
        }

        img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
            margin-bottom: 20px;
            display: block;
            margin: 0 auto;
        }

        h1 {
            text-decoration: none;
            color: #fff;
        }

        h2 {
            margin-bottom: 20px;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        ul li {
            margin-bottom: 5px;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            color: #fff;
            border: none;
            border-radius: 5px;
            margin: 10px;
            background-color: #F97F51;/
        }
    </style>
</head>
<body>
    <h1><a href="${pageContext.request.contextPath}/library?search=all" style="text-decoration: none;">.NETFlix</a></h1>

    <div>
        <%@ include file="success.jsp" %>
        <%@ include file="error.jsp" %>
    </div>

    <div class="film-details">
        <img src="https://image.tmdb.org/t/p/w220_and_h330_face${film.poster}" alt="${film.title} Poster">

        <h2>${film.title} (${film.year})</h2>

        <p><strong>Language:</strong> ${film.lang}</p>
        <p><strong>Support:</strong> ${film.support}</p>
        <p><strong>Release date:</strong> ${film.releaseDate}</p>
        <p><strong>Rate:</strong> ${film.rate}/10</p>
        <p><strong>Opinion:</strong> ${film.opinion}</p>

        <h3>Description</h3>
        <p>${film.description}</p>

        <h3>Director</h3>
        <c:if test="${not empty film.director}">
            <p>${film.director.firstname} ${film.director.lastname}</p>
        </c:if>
        <h3>Actors</h3>
        <ul>
            <c:forEach var="actor" items="${film.actors}" begin="0" end="7">
                <li>${actor.firstname} ${actor.lastname}</li>
            </c:forEach>
        </ul>
        <p>[others actors..]</p>

        <form action="${pageContext.request.contextPath}/film/edit" method="GET" class="inline-buttons">
            <input type="hidden" name="tmdbId" value="${film.id}">
            <button type="submit" style="background-color: #F97F51;">Edit</button>
        </form>
    </div>
</body>
</html>