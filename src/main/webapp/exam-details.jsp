<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.exam.model.Exam" %>
<%
    // Check if user is logged in and is admin
    String role = (String) session.getAttribute("role");
    if (!"admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    Exam exam = (Exam) request.getAttribute("exam");
    if (exam == null) {
        response.sendRedirect("view-exams.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exam Details | Online Examination System</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/modern-dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }
        
        .exam-header {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: var(--border-radius);
            margin-bottom: 30px;
            border-left: 4px solid var(--primary-color);
        }
        
        .exam-title {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .exam-title h3 {
            font-size: 1.4rem;
            margin: 0;
            color: var(--dark-color);
        }
        
        .module-badge {
            background-color: var(--primary-color);
            color: white;
            padding: 5px 12px;
            border-radius: 30px;
            font-size: 12px;
            font-weight: 500;
        }
        
        .exam-info {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 15px;
        }
        
        .info-item {
            display: flex;
            align-items: center;
            color: var(--gray-color);
        }
        
        .info-item i {
            margin-right: 8px;
            color: var(--primary-color);
        }
        
        .exam-description {
            color: var(--gray-color);
            border-top: 1px solid #eee;
            padding-top: 15px;
            margin-bottom: 20px;
        }
        
        .exam-actions {
            display: flex;
            gap: 15px;
            margin-top: 20px;
        }
        
        .exam-actions .button {
            min-width: 150px;
            text-align: center;
        }
        
        .empty-state {
            text-align: center;
            padding: 40px 20px;
            background-color: #f8f9fa;
            border-radius: var(--border-radius);
            color: var(--gray-color);
        }
        
        .empty-state i {
            color: var(--primary-color);
            opacity: 0.7;
            margin-bottom: 15px;
        }
        
        .empty-state h3 {
            margin-bottom: 10px;
            color: var(--dark-color);
        }
        
        @media (max-width: 768px) {
            .exam-title {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .module-badge {
                margin-top: 10px;
            }
            
            .exam-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="sidebar">
            <div class="profile">
                <h3><i class="fas fa-user-shield"></i> Admin Panel</h3>
                <p>Welcome, <%= session.getAttribute("username") %></p>
            </div>
            <ul class="nav-menu">
                <li><a href="admin-dashboard.jsp"><i class="fas fa-tachometer-alt"></i> <span>Dashboard</span></a></li>
                <li class="active"><a href="view-exams.jsp"><i class="fas fa-list-alt"></i> <span>View Exams</span></a></li>
                <li><a href="user-management-view.jsp"><i class="fas fa-users-cog"></i> <span>Manage Users</span></a></li>
                <li><a href="javascript:void(0);" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a></li>
            </ul>
        </div>
        
        <div class="main-content">
            <div class="panel-section">
                <div class="page-header">
                    <h2><i class="fas fa-clipboard-list"></i> Exam Details</h2>
                    <a href="view-exams.jsp" class="button btn-secondary">
                        <i class="fas fa-arrow-left"></i> Back to Exams
                    </a>
                </div>
                
                <div class="exam-header">
                    <div class="exam-title">
                        <h3><%= exam.getTitle() %></h3>
                        <span class="module-badge"><%= exam.getModuleName() %></span>
                    </div>
                    
                    <div class="exam-info">
                        <div class="info-item">
                            <i class="fas fa-clock"></i>
                            <span><%= exam.getDuration() %> minutes</span>
                        </div>
                    </div>
                    
                    <div class="exam-description">
                        <p><%= exam.getDescription() %></p>
                    </div>
                    
                    <div class="exam-actions">
                        <form action="deleteExam" method="post" onsubmit="return confirm('Are you sure you want to delete this exam?');">
                            <input type="hidden" name="examId" value="<%= exam.getId() %>">
                            <button type="submit" class="button btn-danger">
                                <i class="fas fa-trash"></i> Delete Exam
                            </button>
                        </form>
                    </div>
                </div>
                
                <div class="empty-state">
                    <i class="fas fa-clipboard-check fa-3x"></i>
                    <h3>Exam Information</h3>
                    <p>This exam has been set up with basic information. No questions are available in this version.</p>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Auto dismiss alerts if any
        document.addEventListener('DOMContentLoaded', function() {
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
</body>
</html>