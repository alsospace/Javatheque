<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>.NETFlix - Register</title>
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
        }

        input {
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
<body>

<h1><a href="${pageContext.request.contextPath}/">.NETFlix</a></h1>

<form id="register_user" action="register" method="POST">
    <label for="lastname">Name: </label>
    <input type="text" id="lastname" name="lastname" required>

    <label for="firstname">Firstname: </label>
    <input type="text" id="firstname" name="firstname" required>

    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Password: </label>
    <input type="password" id="password" name="password" required>

    <button type="submit">Register</button>
</form>
</body>
</html>
