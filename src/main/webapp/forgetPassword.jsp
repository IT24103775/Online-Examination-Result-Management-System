<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/forget.css">
</head>
<body>
<div class="auth-container">
    <div class="forgot-password-container">
        <h1>Forgot Password</h1>
        <form action="forgot-password" method="post">
            <div class="input-group">
                <label for="email">Username</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="input-group">
                <label for="new_password">New Password</label>
                <input type="password" id="new_password" name="password" required>
            </div>

            <div class="input-group">
                <label for="confirm_password">Conform Password</label>
                <input type="password" id="confirm_password" name="password" required>
            </div>

            <button type="submit" class="auth-button">Reset Password</button>
        </form>
        <div class="switch-auth">
            Remember your password? <a href="index.jsp">Login</a>
        </div>
    </div>
</div>
</body>
</html>