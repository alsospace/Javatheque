<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix - Edit film</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #273c75;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            flex-direction: column;
        }

        a {
            text-decoration: none;
            color: inherit;
        }

        h1 {
            text-align: center;
            color: #f4f4f4;
            margin-top: 0;
        }

        form {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }

        input[readonly],
        select[readonly] {
            background-color: #f0f0f0;
            cursor: not-allowed;
        }

        input,
        select,
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        textarea {
            height: 100px;

        }

        button {
            background-color: #e056fd;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #be2edd;
        }
    </style>

</head>
<body>
    <h1><a href="${pageContext.request.contextPath}/">.NETFlix</a></h1>

    <form id="edit_film" action="${pageContext.request.contextPath}/film/edit" method="POST">
        <label for="id">ID: </label>
        <input type="text" id="id" name="id" value="${filmBean.id}" readonly>

        <label for="title">Title: </label>
        <input type="text" id="title" name="title" value="${filmBean.title}" readonly>

        <label for="lang">Language: </label>
        <select id="lang" name="lang">
            <option value="fr-FR" ${filmBean.lang == 'fr-FR' ? 'selected' : ''}>French (fr-FR)</option>
            <option value="en-US" ${filmBean.lang == 'en-US' ? 'selected' : ''}>English (en-US)</option>
        </select>

        <label for="support">Support: </label>
        <select id="support" name="support">
            <option value="CD" ${filmBean.support == 'CD' ? 'selected' : ''}>CD</option>
            <option value="DVD" ${filmBean.support == 'DVD' ? 'selected' : ''}>DVD</option>
            <option value="Blu-ray" ${filmBean.support == 'Blu-ray' ? 'selected' : ''}>Blu-ray</option>
            <option value="USB" ${filmBean.support == 'USB' ? 'selected' : ''}>USB</option>
            <option value="Streaming" ${filmBean.support == 'Streaming' ? 'selected' : ''}>Streaming</option>
        </select>

        <label for="rate">Rate: </label>
        <input type="text" id="rate" name="rate" value="${filmBean.rate}">

        <label for="opinion">Opinion: </label>
        <textarea id="opinion" name="opinion">${filmBean.opinion}</textarea>

        <button type="submit">Edit</button>
    </form>
</body>
</html>