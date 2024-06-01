<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix - Search Results</title>
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
    <h1>Search Results</h1>
    
    <c:if test="${not empty searchResults}">
        <table>
            <thead>
                <tr>
                    <th>Film</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="movie" items="${searchResults.results}">
                    <tr>
                        <td>
                            <img src="https://image.tmdb.org/t/p/w220_and_h330_face/${movie.poster_path}" alt="${movie.title}" width="100">
                            <p>${movie.title}</p>
                            <p><em>${movie.release_date}</em></p>
                            <form action="{pageContext.request.contextPath}/films/add" method="POST">
                                <input type="hidden" name="tmdbId" value="${movie.id}">
                                <input type="hidden" name="lang" value="${param.lang}">
                                <input type="hidden" name="support" value="${param.support}">
                                <button type="submit">Add to my library</button>
                            </form>
                        </td>
                        <td>${movie.overview}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    
    <c:if test="${empty searchResults.results}">
        <p>No results found.</p>
    </c:if>
    
    <div style="text-align: center; margin-top: 20px; display: flex; justify-content: space-between;">
        <c:if test="${searchResults.page > 1}">
            <form action="{pageContext.request.contextPath}/films/search" method="GET">
                <input type="hidden" name="query" value="${param.query}">
                <input type="hidden" name="language" value="${param.language}">
                <input type="hidden" name="page" value="${searchResults.page - 1}">
                <button type="submit" style="background-color: #2a8f03;"> << Previous Page</button>
            </form>
        </c:if>

        <c:if test="${searchResults.page < searchResults.total_pages}">
            <form action="{pageContext.request.contextPath}/films/search" method="GET">
                <input type="hidden" name="query" value="${param.query}">
                <input type="hidden" name="language" value="${param.language}">
                <input type="hidden" name="page" value="${searchResults.page + 1}">
                <button type="submit" style="background-color: #2a8f03;">Next Page >></button>
            </form>
        </c:if>
    </div>
    
    <div style="text-align: center; margin-top: 20px;">
        <a href="{pageContext.request.contextPath}/films/search">New Search</a>
    </div>
</body>
</html>