<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.exam.util.ExamFileHandler" %>
<%@ page import="com.exam.model.Exam" %>
<%@ page import="java.util.List" %>
<%
    // Check if user is logged in and is admin
    String role = (String) session.getAttribute("role");
    if (!"admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Get all exams
    List<Exam> allExams = ExamFileHandler.getAllExams();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Exams | Online Examination System</title>
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
                <li><a href="admin-dashboard.jsp"><i class="fas fa-tachometer-alt"></i> <span>Dashboard</span></a></li>
                <li class="active"><a href="view-exams.jsp"><i class="fas fa-list-alt"></i> <span>View Exams</span></a></li>
                <li><a href="user-management-view.jsp"><i class="fas fa-users-cog"></i> <span>Manage Users</span></a></li>
                <li><a href="javascript:void(0);" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a></li>
            </ul>
        </div>
        
        <div class="main-content">
            <h2><i class="fas fa-clipboard-list"></i> Exam Management</h2>
            
            <% if(request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <div class="panel-section">
                <div class="search-bar">
                    <input type="text" id="examSearch" placeholder="Search exams by title, module or description...">
                </div>
                
                <div class="exam-count">
                    <i class="fas fa-clipboard-list"></i> Total Exams: <span id="examCount"><%= allExams.size() %></span>
                </div>
                
                <% if(allExams.isEmpty()) { %>
                    <div class="no-exams-message">
                        <i class="fas fa-clipboard"></i>
                        <p>No exams have been created yet.</p>
                        <a href="admin-dashboard.jsp#create-exam" class="button">
                            <i class="fas fa-plus-circle"></i> Create Your First Exam
                        </a>
                    </div>
                <% } else { %>
                    <div class="table-responsive">
                        <table class="exam-table" id="examTable">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Module</th>
                                    <th>Duration</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for(Exam exam : allExams) { %>
                                    <tr>
                                        <td><%= exam.getTitle() %></td>
                                        <td><%= exam.getModuleName() %></td>
                                        <td><%= exam.getDuration() %> min</td>
                                        <td>
                                            <div class="action-buttons">
                                                <form action="examDetails" method="get">
                                                    <input type="hidden" name="examId" value="<%= exam.getId() %>">
                                                    <button type="submit" class="view-button"><i class="fas fa-eye"></i> View</button>
                                                </form>
                                                <form action="deleteExam" method="post" onsubmit="return confirm('Are you sure you want to delete this exam?');">
                                                    <input type="hidden" name="examId" value="<%= exam.getId() %>">
                                                    <button type="submit" class="delete-button"><i class="fas fa-trash"></i> Delete</button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Exam search functionality
            const examSearch = document.getElementById('examSearch');
            const examTable = document.getElementById('examTable');
            const examCount = document.getElementById('examCount');
            
            if(examSearch && examTable) {
                examSearch.addEventListener('keyup', function() {
                    const searchText = this.value.toLowerCase();
                    const rows = examTable.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
                    let visibleCount = 0;
                    
                    for (let i = 0; i < rows.length; i++) {
                        const title = rows[i].getElementsByTagName('td')[0].textContent.toLowerCase();
                        const module = rows[i].getElementsByTagName('td')[1].textContent.toLowerCase();
                        
                        if (title.includes(searchText) || module.includes(searchText)) {
                            rows[i].style.display = '';
                            visibleCount++;
                        } else {
                            rows[i].style.display = 'none';
                        }
                    }
                    
                    examCount.textContent = visibleCount;
                });
            }
            
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
</body>
</html>
