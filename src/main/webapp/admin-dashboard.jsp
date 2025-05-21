<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.exam.util.ExamFileHandler" %>
<%@ page import="com.exam.util.FileHandler" %>
<%@ page import="com.exam.model.Exam" %>
<%@ page import="com.exam.model.User" %>
<%@ page import="java.util.List" %>
<%
    // Check if user is logged in and is admin
    String role = (String) session.getAttribute("role");
    if (!"admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Online Examination System</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/modern-dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="dashboard-container">
        <div class="sidebar">
            <div class="profile">
                <h3><i class="fas fa-user-shield"></i> Admin Panel</h3>
                <p>Welcome, <%= session.getAttribute("username") %></p>
            </div>
            <ul class="nav-menu">
                <li class="active"><a href="admin-dashboard.jsp"><i class="fas fa-tachometer-alt"></i> <span>Dashboard</span></a></li>
                <li><a href="view-exams.jsp"><i class="fas fa-list-alt"></i> <span>View Exams</span></a></li>
                <li><a href="user-management-view.jsp"><i class="fas fa-users-cog"></i> <span>Manage Users</span></a></li>
                <li><a href="javascript:void(0);" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a></li>
            </ul>
        </div>
        
        <div class="main-content">
            <% if(request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %>
                    <% if(request.getAttribute("successMessage").toString().contains("Exam created")) { %>
                        <p>You can <a href="view-exams.jsp">view all exams here</a>.</p>
                    <% } %>
                </div>
            <% } %>
            
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <div class="panel-section">
                <h2><i class="fas fa-tachometer-alt"></i> Admin Dashboard</h2>
                <div class="dashboard-stats">
                    <div class="stat-card">
                        <div class="stat-icon"><i class="fas fa-file-alt"></i></div>
                        <div class="stat-info">
                            <h3>Total Exams</h3>
                            <p><%= ExamFileHandler.getAllExams().size() %></p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon"><i class="fas fa-users"></i></div>
                        <div class="stat-info">
                            <h3>Total Users</h3>
                            <p><%= FileHandler.getAllUsers().size() %></p>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="create-exam" class="panel-section">
                <h2><i class="fas fa-file-medical"></i> Create New Exam</h2>
                <p>Create an exam for your selected module with the details below.</p>
                
                <form action="exam" method="post">
                    <input type="hidden" name="action" value="createExam">
                    
                    <div class="form-group">
                        <label for="moduleName"><i class="fas fa-book"></i> Select Module:</label>
                        <select id="moduleName" name="moduleName" required>
                            <option value="">Select a module</option>
                            <option value="Object Oriented Programming">Object Oriented Programming</option>
                            <option value="Data Structures and Algorithms">Data Structures and Algorithms</option>
                            <option value="Technical Writing">Technical Writing</option>
                            <option value="Discrete Mathematics">Discrete Mathematics</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="title"><i class="fas fa-heading"></i> Exam Title:</label>
                        <input type="text" id="title" name="title" placeholder="Enter a descriptive title" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description"><i class="fas fa-align-left"></i> Exam Description:</label>
                        <textarea id="description" name="description" rows="3" placeholder="Provide details about the exam" required></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="duration"><i class="fas fa-clock"></i> Duration (minutes):</label>
                        <input type="number" id="duration" name="duration" min="10" max="180" value="60" required>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit"><i class="fas fa-plus-circle"></i> Create Exam</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Auto dismiss alerts after 5 seconds
            const alerts = document.querySelectorAll('.success-message, .error-message');
            alerts.forEach(alert => {
                setTimeout(() => {
                    alert.style.opacity = '0';
                    alert.style.transition = 'opacity 0.3s ease';
                    setTimeout(() => {
                        alert.style.display = 'none';
                    }, 300);
                }, 5000);
            });
        });
        
        // Logout confirmation function
        function confirmLogout() {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = "logout";
            }
        }
    </script>
    
    <style>
        .dashboard-stats {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        
        .stat-card {
            flex: 1;
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            padding: 20px;
            display: flex;
            align-items: center;
        }
        
        .stat-icon {
            width: 60px;
            height: 60px;
            background-color: rgba(67, 97, 238, 0.1);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 20px;
            color: var(--primary-color);
            font-size: 24px;
        }
        
        .stat-info h3 {
            font-size: 16px;
            margin-bottom: 5px;
            color: var(--gray-color);
        }
        
        .stat-info p {
            font-size: 28px;
            font-weight: bold;
            color: var(--dark-color);
            margin: 0;
        }
        
        @media (max-width: 768px) {
            .dashboard-stats {
                flex-direction: column;
            }
        }
    </style>
</body>
</html>
