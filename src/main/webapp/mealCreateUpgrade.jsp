<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal_Create_Upgrade</title>
</head>
<body>
    <form method="post" action="meals" name="Meal Create/Upgrade">
        Meal Date: <input type="datetime-local" name="dateTime"> <br>
        Description: <input type="text" name="description"> <br>
        Calories: <input type="number" name="calories"> <br>
       <input type="submit" value="Create"/>
    </form>

</body>
</html>
