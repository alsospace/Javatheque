<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <title>.NETFlix - Search</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #273c75;
            color: #f4f4f4;
            margin: 20px;
        }

        h1 {
            color: #f4f4f4;
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #273c75;
            color: #f4f4f4;
        }

        img {
            max-width: 100%;
            height: auto;
            display: block;
            margin: 0 auto;
            border-radius: 5px;
        }

        button {
            background-color: #0da080;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 3px;
            display: block;
            margin: 0 auto;
        }

        button:hover {
            background-color: #0da080;
        }

        td p {
            margin: 5px 0;
            text-align: center;
        }

        td:first-child {
            max-width: 150px;
            text-align: center;
        }

        td:last-child {
            max-width: 500px;
        }

        @media (max-width: 600px) {
            td {
                display: block;
                text-align: center;
            }

            td img {
                margin-bottom: 10px;
            }

            button {
                display: block;
                margin: 10px auto;
            }
        }
    </style>

</head>
<body>
    <h1 style="display: flex; align-items: center; justify-content: space-between;">
        Result for: ${param.title}
        <a href="/films/search" style="text-decoration: none;">
            <button style="background-color: #db34c5;">Back to Search</button>
        </a>
    </h1>
    <table>
        <thead>
            <tr>
                <th>Film</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="film" items="${films}">
                <tr>
                    <td>
                        <img src="https://image.tmdb.org/t/p/w220_and_h330_face/${film.posterPath}" alt="${film.title}" width="100">
                        <p>${film.title}</p>
                        <p><em>${film.releaseDate}</em></p>
                        <form action="/films/add" method="POST">
                            <input type="hidden" name="tmdbId" value="${film.id}">
                            <input type="hidden" name="lang" value="${param.lang}">
                            <input type="hidden" name="support" value="${param.support}">
                            <button type="submit">Add to my library</button>
                        </form>
                    </td>
                    <td>${film.overview}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div style="text-align: center; margin-top: 20px; display: flex; justify-content: space-between;">
        <c:if test="${prevPage}">
            <form action="/search_existant_films" method="GET">
                <input type="hidden" name="title" value="${param.title}">
                <input type="hidden" name="support" value="${param.support}">
                <input type="hidden" name="lang" value="${param.lang}">
                <input type="hidden" name="page" value="${prevPage}">
                <button type="submit" style="background-color: #2a8f03;"> << Previous Page</button>
            </form>
        </c:if>

        <c:if test="${nextPage}">
            <form action="/search_existant_films" method="GET">
                <input type="hidden" name="title" value="${param.title}">
                <input type="hidden" name="support" value="${param.support}">
                <input type="hidden" name="lang" value="${param.lang}">
                <input type="hidden" name="page" value="${nextPage}">
                <button type="submit" style="background-color: #2a8f03;">Next Page >></button>
            </form>
        </c:if>
    </div>
</body>
</html>