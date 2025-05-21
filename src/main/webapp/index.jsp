<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | Online Examination System</title>
    <link rel="stylesheet" type="text/css" href="css/admin-login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="login-container">
        <div class="login-image">
            <h1>Online Examination System</h1>
            <p>Securely manage exams and assessments with our advanced administrative tools.</p>
            
            <div class="exam-icons">
                <div class="exam-icon">
                    <i class="fas fa-book"></i>
                </div>
                <div class="exam-icon">
                    <i class="fas fa-graduation-cap"></i>
                </div>
                <div class="exam-icon">
                    <i class="fas fa-chart-line"></i>
                </div>
            </div>
        </div>
        
        <div class="login-form">
            <div class="form-wrapper">
                <div class="form-header">
                    <h2><i class="fas fa-user-shield"></i> Admin Portal</h2>
                    <p>Please login to access the examination dashboard</p>
                </div>
                
                <% if(request.getAttribute("errorMessage") != null) { %>
                    <div class="error-message">
                        <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("errorMessage") %>
                    </div>
                <% } %>
                
                <form action="login" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <div class="input-group">
                            <input type="text" id="username" name="username" placeholder="Enter your username" required>
                            <i class="fas fa-user"></i>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-group">
                            <input type="password" id="password" name="password" placeholder="Enter your password" required>
                            <i class="fas fa-lock"></i>
                        </div>
                    </div>
                    
                    <button type="submit" class="submit-btn">
                        <i class="fas fa-sign-in-alt"></i> Sign In
                    </button>
                </form>
                
                <div class="extra-links">
                    <a href="#"><i class="fas fa-question-circle"></i> Need Help?</a>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Add simple animations and interactivity
        document.addEventListener('DOMContentLoaded', function() {
            const inputs = document.querySelectorAll('input');
            inputs.forEach(input => {
                input.addEventListener('focus', function() {
                    this.parentElement.style.transform = 'scale(1.02)';
                    this.parentElement.style.transition = 'transform 0.3s';
                });
                
                input.addEventListener('blur', function() {
                    this.parentElement.style.transform = 'scale(1)';
                });
            });
        });
    </script>
</body>
</html>
