<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add a new Task</title>
    <link rel="stylesheet" type="text/css" href="CSS/add-task.css">
</head>
<body>
    <h2>Add a new Task</h2>
    <form action="add-task" method="post">
        <label>Project Name</label>
        <input type="text" name="project-name" required/><br><br>
        
        <label>Role</label>
        <input type="text" name="task-role" required/><br><br>
        
        <label>Date</label>
        <input type="date" name="task-date" required/><br><br>
        
        <label>Start Time</label>
        <input type="time" name="task-start-time" required/><br><br>
        
        <label>End Time</label>
        <input type="time" name="task-end-time" required/><br><br>
        
        <label>Task Category</label>
        <select name="task-category" required>
            <option value="Checking">Checking</option>
            <option value="Coding">Coding</option>
            <option value="Production">Production</option>
            <option value="Testing">Testing</option>
            <option value="Meeting">Meeting</option>
            <option value="Designing">Designing</option>
            <option value="Debugging">Debugging</option>
        </select><br><br>
        
        <label>Description</label>
        <textarea name="task-description" required></textarea><br><br>   
        
        <input type="submit" value="Add"/>     
    </form>
</body>
</html>
