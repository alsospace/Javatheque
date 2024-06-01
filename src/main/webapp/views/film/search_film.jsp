<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix - Search film</title>
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

        input,
        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
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
</head>
<body>
    <h1><a href="${pageContext.request.contextPath}/">.NETFlix</a></h1>

    <form id="search_existant_films" action="${pageContext.request.contextPath}/search_existant_films" method="GET">
        <label for="title">Title: </label>
        <input type="text" id="title" name="title" required>

        <label for="lang">Language: </label>
        <select id="lang" name="lang">
            <option value="fr-FR" selected>French (fr-FR)</option>
            <option value="en-US">English (en-US)</option>
        </select>

        <label for="support">Support: </label>
        <select id="support" name="support">
            <option value="CD" selected>CD</option>
            <option value="DVD">DVD</option>
            <option value="Blu-ray">Blu-ray</option>
            <option value="USB">USB</option>
            <option value="Streaming">Streaming</option>
        </select>
        <input type="hidden" name="page" value="1">

        <button type="submit">Search</button>
    </form>
</body>
</html>