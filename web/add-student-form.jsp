<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add student</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>Foobar University</h2>
    </div>
</div>

<div id="container">
    <h3>Add Student</h3>

    <form action="StudentControllerServlet" method="POST">
        <input type="hidden" name="command" value="ADD">

        <table>
            <tbody>
            <tr>
                <td><label>First Name: </label></td>
                <td><input type="text" name="firstName"></td>
            </tr>
            <tr>
                <td><label>Last Name: </label></td>
                <td><input type="text" name="lastName"></td>
            </tr>
            <tr>
                <td><label>Email: </label></td>
                <td><input type="email" name="email"></td>
            </tr>
            <tr>
                <td><input type="submit" value="save" class="save"></td>
            </tr>
            </tbody>
        </table>
    </form>

    <div style="clear:both;">

        <p>
            <a href="/StudentControllerServlet">Back to list</a>
        </p>
    </div>
</body>
</html>
