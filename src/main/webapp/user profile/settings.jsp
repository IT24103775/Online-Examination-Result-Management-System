<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Site Exam - Settings</title>
    <link rel="stylesheet" href="Home/Css/settings.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/Dashboard.jsp"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
    <a href="${pageContext.request.contextPath}/exams.jsp"><i class="fas fa-clipboard-list"></i> Exams</a>
    <a href="${pageContext.request.contextPath}/results.jsp"><i class="fas fa-chart-bar"></i> Results</a>
    <a href="${pageContext.request.contextPath}/UserList.jsp"><i class="fas fa-users"></i> Students</a>
    <c:>
        <c: test="${not empty sessionScope.admin}">
            <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </c:>
        <c:>
            <a href="${pageContext.request.contextPath}/admin-login.jsp"><i class="fas fa-sign-in-alt"></i> Admin Login</a>
        </c:>
    </c:>
    <a class="active" href="${pageContext.request.contextPath}/settings.jsp"><i class="fas fa-cog"></i> Settings</a>
</div>

<div class="settings-container">
    <h1><i class="fas fa-cog"></i> Settings</h1>

    <div class="settings-tabs">
        <button class="tab-button active" onclick="openTab(event, 'account')">
            <i class="fas fa-user"></i> Account
        </button>
        <button class="tab-button" onclick="openTab(event, 'notification')">
            <i class="fas fa-bell"></i> Notifications
        </button>
        <button class="tab-button" onclick="openTab(event, 'security')">
            <i class="fas fa-shield-alt"></i> Security
        </button>
        <button class="tab-button" onclick="openTab(event, 'appearance')">
            <i class="fas fa-palette"></i> Appearance
        </button>
    </div>

    <div id="account" class="tab-content" style="display: block;">
        <h2><i class="fas fa-user-circle"></i> Account Information</h2>
        <form id="accountForm" action="${pageContext.request.contextPath}/UpdateAccount" method="post">
            <div class="form-group">
                <label for="username"><i class="fas fa-user-tag"></i> Username</label>
                <input type="text" id="username" name="username" value="${sessionScope.admin.username}" readonly>
            </div>
            <div class="form-group">
                <label for="email"><i class="fas fa-envelope"></i> Email</label>
                <input type="email" id="email" name="email" value="${sessionScope.admin.email}" required>
            </div>
            <div class="form-group">
                <label for="fullname"><i class="fas fa-id-card"></i> Full Name</label>
                <input type="text" id="fullname" name="fullname" value="${sessionScope.admin.fullName}" required>
            </div>
            <button type="submit" class="save-btn"><i class="fas fa-save"></i> Save Changes</button>
            <div id="accountMessage" class="message"></div>
        </form>
    </div>

    <div id="notification" class="tab-content">
        <h2><i class="fas fa-bell"></i> Notification Preferences</h2>
        <form id="notificationForm" action="${pageContext.request.contextPath}/UpdateNotifications" method="post">
            <div class="form-group checkbox-group">
                <input type="checkbox" id="emailNotifications" name="emailNotifications" ${sessionScope.admin.notifications.email ? 'checked' : ''}>
                <label for="emailNotifications"><i class="fas fa-envelope"></i> Email Notifications</label>
            </div>
            <div class="form-group checkbox-group">
                <input type="checkbox" id="systemNotifications" name="systemNotifications" ${sessionScope.admin.notifications.system ? 'checked' : ''}>
                <label for="systemNotifications"><i class="fas fa-desktop"></i> System Notifications</label>
            </div>
            <div class="form-group checkbox-group">
                <input type="checkbox" id="examUpdates" name="examUpdates" ${sessionScope.admin.notifications.examUpdates ? 'checked' : ''}>
                <label for="examUpdates"><i class="fas fa-clipboard-list"></i> Exam Updates</label>
            </div>
            <div class="form-group checkbox-group">
                <input type="checkbox" id="resultAlerts" name="resultAlerts" ${sessionScope.admin.notifications.resultAlerts ? 'checked' : ''}>
                <label for="resultAlerts"><i class="fas fa-chart-bar"></i> Result Alerts</label>
            </div>
            <button type="submit" class="save-btn"><i class="fas fa-save"></i> Save Preferences</button>
            <div id="notificationMessage" class="message"></div>
        </form>
    </div>

    <div id="security" class="tab-content">
        <h2><i class="fas fa-shield-alt"></i> Security Settings</h2>
        <form id="securityForm" action="${pageContext.request.contextPath}/UpdatePassword" method="post">
            <div class="form-group">
                <label for="currentPassword"><i class="fas fa-lock"></i> Current Password</label>
                <input type="password" id="currentPassword" name="currentPassword" required>
                <i class="fas fa-eye toggle-password" onclick="togglePassword('currentPassword')"></i>
            </div>
            <div class="form-group">
                <label for="newPassword"><i class="fas fa-key"></i> New Password</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <i class="fas fa-eye toggle-password" onclick="togglePassword('newPassword')"></i>
                <div class="password-strength">
                    <span id="strengthText">Password strength: </span>
                    <span id="strengthBar"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="confirmPassword"><i class="fas fa-key"></i> Confirm New Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <i class="fas fa-eye toggle-password" onclick="togglePassword('confirmPassword')"></i>
            </div>
            <button type="submit" class="save-btn"><i class="fas fa-save"></i> Change Password</button>
            <div id="securityMessage" class="message"></div>
        </form>
    </div>

    <div id="appearance" class="tab-content">
        <h2><i class="fas fa-palette"></i> Appearance Settings</h2>
        <form id="appearanceForm" action="${pageContext.request.contextPath}/UpdateAppearance" method="post">
            <div class="form-group">
                <label for="theme"><i class="fas fa-moon"></i> Theme</label>
                <select id="theme" name="theme">
                    <option value="light" ${sessionScope.admin.theme == 'light' ? 'selected' : ''}>Light</option>
                    <option value="dark" ${sessionScope.admin.theme == 'dark' ? 'selected' : ''}>Dark</option>
                    <option value="system" ${sessionScope.admin.theme == 'system' ? 'selected' : ''}>System Default</option>
                </select>
            </div>
            <div class="form-group">
                <label for="fontSize"><i class="fas fa-font"></i> Font Size</label>
                <input type="range" id="fontSize" name="fontSize" min="12" max="24" value="${not empty sessionScope.admin.fontSize ? sessionScope.admin.fontSize : 16}">
                <span id="fontSizeValue">${not empty sessionScope.admin.fontSize ? sessionScope.admin.fontSize : 16}px</span>
            </div>
            <div class="form-group">
                <label><i class="fas fa-paint-brush"></i> Accent Color</label>
                <div class="color-options">
                    <input type="radio" id="colorBlue" name="accentColor" value="blue" ${sessionScope.admin.accentColor == 'blue' ? 'checked' : ''}>
                    <label for="colorBlue" class="color-option blue"></label>

                    <input type="radio" id="colorGreen" name="accentColor" value="green" ${sessionScope.admin.accentColor == 'green' ? 'checked' : ''}>
                    <label for="colorGreen" class="color-option green"></label>

                    <input type="radio" id="colorPurple" name="accentColor" value="purple" ${sessionScope.admin.accentColor == 'purple' ? 'checked' : ''}>
                    <label for="colorPurple" class="color-option purple"></label>

                    <input type="radio" id="colorRed" name="accentColor" value="red" ${sessionScope.admin.accentColor == 'red' ? 'checked' : ''}>
                    <label for="colorRed" class="color-option red"></label>
                </div>
            </div>
            <button type="submit" class="save-btn"><i class="fas fa-save"></i> Apply Changes</button>
            <div id="appearanceMessage" class="message"></div>
        </form>
    </div>
</div>

<script src="Home/Js/settings.js"></script>
</body>
</html>