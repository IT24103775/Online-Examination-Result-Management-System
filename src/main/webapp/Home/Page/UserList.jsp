<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management System</title>
    <link rel="stylesheet" href="../../Home/Css/User.css">
</head>
<body>
<div class="container">
    <h1>User Management</h1>

    <!-- Add User Button -->
    <div class="action-bar">
        <button id="addUserBtn" class="btn btn-primary">Add New User</button>
    </div>

    <!-- User Table -->
    <table id="userTable" class="user-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Sample data - in real app this would come from server -->
        <tr>
            <td>1</td>
            <td>Sriyan</td>
            <td>Chanuka</td>
            <td>it24103775@my.sliit.lk</td>
            <td>Admin</td>
            <td><span class="status active">Active</span></td>
            <td>
                <button class="btn btn-edit">Edit</button>
                <button class="btn btn-delete">Delete</button>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>Bawani</td>
            <td>Adhithya</td>
            <td>it24102778@my.sliit.lk</td>
            <td>User</td>
            <td><span class="status inactive">Inactive</span></td>
            <td>
                <button class="btn btn-edit">Edit</button>
                <button class="btn btn-delete">Delete</button>
            </td>
        </tr>
        <tr>
            <td>3</td>
            <td>Viraj</td>
            <td>Buddika</td>
            <td>it24103761@my.sliit.lk</td>
            <td>Manager</td>
            <td><span class="status active">Active</span></td>
            <td>
                <button class="btn btn-edit">Edit</button>
                <button class="btn btn-delete">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<!-- User Modal (for add/edit) -->
<div id="userModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2 id="modalTitle">Add New User</h2>
        <form id="userForm">
            <input type="hidden" id="userId">
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="role">Role</label>
                <select id="role" name="role" required>
                    <option value="">Select Role</option>
                    <option value="Admin">Admin</option>
                    <option value="Manager">Manager</option>
                    <option value="User">User</option>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                </select>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-cancel" id="cancelBtn">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h2>Confirm Delete</h2>
        <p>Are you sure you want to delete this user?</p>
        <input type="hidden" id="deleteUserId">
        <div class="modal-actions">
            <button id="confirmDeleteBtn" class="btn btn-danger">Delete</button>
            <button id="cancelDeleteBtn" class="btn btn-cancel">Cancel</button>
        </div>
    </div>
</div>

<script src="../../Home/Js/User.js"></script>
</body>
</html>