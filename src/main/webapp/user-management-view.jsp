<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.exam.util.FileHandler" %>
<%@ page import="com.exam.model.User" %>
<%@ page import="java.util.List" %>
<%
    // Check if user is logged in and is admin
    String role = (String) session.getAttribute("role");
    if (!"admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Get all users
    List<User> allUsers = FileHandler.getAllUsers();
    
    // Get success and error messages from session instead of request
    String successMessage = (String) session.getAttribute("successMessage");
    String errorMessage = (String) session.getAttribute("errorMessage");
    
    // Clear messages from session after reading them to prevent reappearing on refresh
    session.removeAttribute("successMessage");
    session.removeAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management | Online Examination System</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/modern-dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        .action-panel {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            gap: 20px;
        }
        
        .action-panel .search-bar {
            flex: 1;
            margin-bottom: 0;
        }
        
        .btn-primary {
            height: 38px;
            min-width: 140px;
            padding: 0 15px;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.2s ease;
        }
        
        .btn-primary i {
            margin-right: 8px;
            font-size: 16px;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        
        .action-btn {
            background-color: #4e73df;
            color: white;
            border: none;
            cursor: pointer;
        }
        
        .text-danger {
            color: var(--danger-color);
        }
        
        @media (max-width: 768px) {
            .action-panel {
                flex-direction: column;
            }
            
            .btn-primary {
                width: 100%;
                max-width: 200px;
            }
        }
        
        .user-management-container {
            padding: 0;
            background-color: transparent;
            box-shadow: none;
        }

        /* Ensure modal appears on top and has proper z-index */
        .modal {
            z-index: 1050;
        }
        
        .modal-content {
            animation: modalSlideIn 0.3s ease;
        }
        
        /* Modal animation for better UX */
        @keyframes modalSlideIn {
            from { transform: translateY(-30px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
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
                <li><a href="view-exams.jsp"><i class="fas fa-list-alt"></i> <span>View Exams</span></a></li>
                <li class="active"><a href="user-management-view.jsp"><i class="fas fa-users-cog"></i> <span>Manage Users</span></a></li>
                <li><a href="javascript:void(0);" onclick="confirmLogout()"><i class="fas fa-sign-out-alt"></i> <span>Logout</span></a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2><i class="fas fa-users"></i> User Management</h2>
            
            <% if(successMessage != null) { %>
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> <%= successMessage %>
                </div>
            <% } %>
            
            <% if(errorMessage != null) { %>
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> <%= errorMessage %>
                </div>
            <% } %>
            
            <div class="panel-section">
                <div class="user-management-container">
                    <div class="action-panel">
                        <button class="btn btn-primary action-btn" onclick="openAddUserModal()">
                            <i class="fas fa-user-plus"></i> Add User
                        </button>
                        
                        <div class="search-bar">
                            <input type="text" id="userSearch" placeholder="Search users by username, email or role...">
                        </div>
                    </div>
                    
                    <div class="user-count">
                        <i class="fas fa-users"></i> Total Users: <span id="userCount"><%= allUsers.size() %></span>
                    </div>
                    
                    <% if(allUsers.isEmpty()) { %>
                        <div class="no-users-message">
                            <i class="fas fa-user-slash"></i>
                            <p>No users found in the system.</p>
                        </div>
                    <% } else { %>
                        <div class="table-responsive">
                            <table class="user-table" id="userTable">
                                <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for(User user : allUsers) { %>
                                        <tr data-username="<%= user.getUsername() %>"
                                            data-email="<%= user.getEmail() %>"
                                            data-role="<%= user.getRole() %>">
                                            <td><%= user.getUsername() %></td>
                                            <td><%= user.getEmail() %></td>
                                            <td>
                                                <span class="role-badge role-<%= user.getRole() %>">
                                                    <%= user.getRole() %>
                                                </span>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <button type="button" class="edit-button" 
                                                            data-username="<%= user.getUsername() %>"
                                                            data-email="<%= user.getEmail() %>"
                                                            data-role="<%= user.getRole() %>"
                                                            onclick="openEditModalFixed(this)">
                                                        <i class="fas fa-edit"></i> Edit
                                                    </button>
                                                    <button class="delete-button" onclick="openDeleteModal('<%= user.getUsername() %>')">
                                                        <i class="fas fa-trash-alt"></i> Delete
                                                    </button>
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
    </div>
    
    <!-- Edit User Modal -->
    <div id="editUserModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3><i class="fas fa-user-edit"></i> Edit User</h3>
                <span class="close" onclick="closeEditModal()">&times;</span>
            </div>
            <form action="<%= request.getContextPath() %>/userManage" method="post" class="modal-form">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="editUsername" name="username">
                
                <div class="form-group">
                    <label for="editNewUsername"><i class="fas fa-user"></i> Username:</label>
                    <input type="text" id="editNewUsername" name="newUsername" required>
                </div>
                
                <div class="form-group">
                    <label for="editPassword"><i class="fas fa-lock"></i> Password:</label>
                    <input type="password" id="editPassword" name="password" placeholder="Enter new password or leave unchanged">
                    <small>Leave blank to keep current password</small>
                </div>
                
                <div class="form-group">
                    <label for="editEmail"><i class="fas fa-envelope"></i> Email:</label>
                    <input type="email" id="editEmail" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="editRole"><i class="fas fa-user-tag"></i> Role:</label>
                    <select id="editRole" name="role" required>
                        <option value="student">Student</option>
                        <option value="teacher">Teacher</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeEditModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Update User
                    </button>
                </div>
            </form>
        </div>
    </div>
    
    <!-- Delete User Modal -->
    <div id="deleteUserModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3><i class="fas fa-user-minus"></i> Delete User</h3>
                <span class="close" onclick="closeDeleteModal()">&times;</span>
            </div>
            
            <div class="modal-form">
                <p>Are you sure you want to delete the user <strong id="deleteUserName"></strong>?</p>
                <p class="text-danger">This action cannot be undone.</p>
                
                <form action="<%= request.getContextPath() %>/userManage" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" id="deleteUsername" name="username">
                    
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeDeleteModal()">
                            <i class="fas fa-times"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-trash-alt"></i> Delete User
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- Add User Modal -->
    <div id="addUserModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3><i class="fas fa-user-plus"></i> Add New User</h3>
                <span class="close" onclick="closeAddUserModal()">&times;</span>
            </div>
            <form action="<%= request.getContextPath() %>/userManage" method="post" class="modal-form">
                <input type="hidden" name="action" value="add">
                
                <div class="form-group">
                    <label for="addUsername"><i class="fas fa-user"></i> Username:</label>
                    <input type="text" id="addUsername" name="username" required>
                </div>
                
                <div class="form-group">
                    <label for="addPassword"><i class="fas fa-lock"></i> Password:</label>
                    <input type="password" id="addPassword" name="password" required>
                </div>
                
                <div class="form-group">
                    <label for="addEmail"><i class="fas fa-envelope"></i> Email:</label>
                    <input type="email" id="addEmail" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="addRole"><i class="fas fa-user-tag"></i> Role:</label>
                    <select id="addRole" name="role" required>
                        <option value="student">Student</option>
                        <option value="teacher">Teacher</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeAddUserModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-plus-circle"></i> Add User
                    </button>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // User search functionality
        document.addEventListener('DOMContentLoaded', function() {
            const userSearch = document.getElementById('userSearch');
            const userTable = document.getElementById('userTable');
            const userCount = document.getElementById('userCount');
            
            if(userSearch && userTable) {
                userSearch.addEventListener('keyup', function() {
                    const searchText = this.value.toLowerCase();
                    const rows = userTable.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
                    let visibleCount = 0;
                    
                    for (let i = 0; i < rows.length; i++) {
                        const username = rows[i].getElementsByTagName('td')[0].textContent.toLowerCase();
                        const email = rows[i].getElementsByTagName('td')[1].textContent.toLowerCase();
                        const role = rows[i].getElementsByTagName('td')[2].textContent.toLowerCase();
                        
                        if (username.includes(searchText) || email.includes(searchText) || role.includes(searchText)) {
                            rows[i].style.display = '';
                            visibleCount++;
                        } else {
                            rows[i].style.display = 'none';
                        }
                    }
                    
                    userCount.textContent = visibleCount;
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
        
        // Edit Modal Functions
        const editUserModal = document.getElementById('editUserModal');
        
        function openEditModal(username) {
            const row = document.querySelector(`tr[data-username="${username}"]`);
            
            if (!row) {
                console.error('User row not found:', username);
                return;
            }
            
            // Debug output to console
            console.log('Opening edit modal for user:', username);
            console.log('Row data:', {
                username: row.getAttribute('data-username'),
                email: row.getAttribute('data-email'),
                role: row.getAttribute('data-role')
            });
            
            // Populate form with user data
            document.getElementById('editUsername').value = username;
            document.getElementById('editNewUsername').value = username;
            document.getElementById('editEmail').value = row.getAttribute('data-email');
            document.getElementById('editPassword').value = '';  // Don't show existing password
            document.getElementById('editRole').value = row.getAttribute('data-role');
            
            // Show the modal
            editUserModal.style.display = 'block';
        }
        
        function openEditModalFixed(button) {
            const username = button.getAttribute('data-username');
            const email = button.getAttribute('data-email');
            const role = button.getAttribute('data-role');
            
            console.log('Opening edit modal with direct data:', {username, email, role});
            
            // Populate form with user data
            document.getElementById('editUsername').value = username;
            document.getElementById('editNewUsername').value = username;
            document.getElementById('editEmail').value = email;
            document.getElementById('editPassword').value = '';
            document.getElementById('editRole').value = role;
            
            // Show the modal
            editUserModal.style.display = 'block';
        }
        
        function closeEditModal() {
            editUserModal.style.display = 'none';
        }
        
        // Delete Modal Functions
        const deleteUserModal = document.getElementById('deleteUserModal');
        
        function openDeleteModal(username) {
            document.getElementById('deleteUsername').value = username;
            document.getElementById('deleteUserName').textContent = username;
            
            // Show the modal
            deleteUserModal.style.display = 'block';
        }
        
        function closeDeleteModal() {
            deleteUserModal.style.display = 'none';
        }
        
        // Add User Modal Functions
        const addUserModal = document.getElementById('addUserModal');
        
        function openAddUserModal() {
            // Reset the form
            document.getElementById('addUsername').value = '';
            document.getElementById('addPassword').value = '';
            document.getElementById('addEmail').value = '';
            document.getElementById('addRole').value = 'student';
            
            // Show the modal
            addUserModal.style.display = 'block';
        }
        
        function closeAddUserModal() {
            addUserModal.style.display = 'none';
        }
        
        // Close modals when clicking outside
        window.onclick = function(event) {
            if (event.target == editUserModal) {
                closeEditModal();
            }
            if (event.target == deleteUserModal) {
                closeDeleteModal();
            }
            if (event.target == addUserModal) {
                closeAddUserModal();
            }
        }
        
        // Logout confirmation function
        function confirmLogout() {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = "logout";
            }
        }
    </script>
</body>
</html>
