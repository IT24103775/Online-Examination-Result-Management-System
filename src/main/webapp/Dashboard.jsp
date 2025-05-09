<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard | Online Exam System</title>
  <link rel="stylesheet" href="Home/Css/Home.css">
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">


</head>
<body>
<div class="dashboard-container">
  <!-- Sidebar -->
  <div class="sidebar">
    <div class="logo-container">
      <img src="Login/IMG/LOGO.png" alt="Site Logo" style="background-color: white;">
      <h2>Site Exam</h2>
    </div>

    <nav class="sidebar-nav">
      <ul>
        <li class="active">
          <a href="#">
            <i class="fas fa-tachometer-alt"></i>
            <span>Dashboard</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="fas fa-book"></i>
            <span>Exams</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="fas fa-clipboard-list"></i>
            <span>Results</span>
          </a>
        </li>
        <li>
          <a href="Home/Page/UserList.jsp">
            <i class="fas fa-users"></i>
            <span>Students</span>
          </a>
        </li>
        <li>
          <a href="#">
            <i class="fas fa-cog"></i>
            <span>Settings</span>
          </a>
        </li>
      </ul>
    </nav>

    <div class="sidebar-footer">
      <div class="user-profile">
        <img src="Dashboard/img/user-avatar.png" alt="User Avatar">
        <div class="user-info">
          <span class="user-name">John Doe</span>
          <span class="user-role">Admin</span>
        </div>
      </div>
      <a href="index.jsp" class="logout-btn">
        <i class="fas fa-sign-out-alt"></i>
        <span>Logout</span>
      </a>
    </div>
  </div>

  <!-- Main Content -->
  <div class="main-content">
    <header class="top-bar">
      <div class="search-bar">
        <i class="fas fa-search"></i>
        <input type="text" placeholder="Search...">
      </div>

    </header>

    <div class="content-wrapper">
      <h1 class="page-title">Dashboard</h1>

      <!-- Stats Cards -->
      <div class="stats-cards">
        <div class="stat-card">
          <div class="stat-icon bg-blue">
            <i class="fas fa-users"></i>
          </div>
          <div class="stat-info">
            <h3>Total Students</h3>
            <p>1,245</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon bg-green">
            <i class="fas fa-book"></i>
          </div>
          <div class="stat-info">
            <h3>Active Exams</h3>
            <p>12</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon bg-orange">
            <i class="fas fa-clipboard-check"></i>
          </div>
          <div class="stat-info">
            <h3>Completed Exams</h3>
            <p>356</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon bg-purple">
            <i class="fas fa-chart-line"></i>
          </div>
          <div class="stat-info">
            <h3>Pass Rate</h3>
            <p>87%</p>
          </div>
        </div>
      </div>

      <!-- Recent Activity and Upcoming Exams -->
      <div class="content-row">
        <div class="content-col">
          <div class="content-card">
            <div class="card-header">
              <h3>Recent Activity</h3>
              <a href="#" class="view-all">View All</a>
            </div>
            <div class="activity-list">
              <div class="activity-item">
                <div class="activity-icon bg-blue">
                  <i class="fas fa-user-plus"></i>
                </div>
                <div class="activity-details">
                  <p>New student registered - Michael Brown</p>
                  <span class="activity-time">10 minutes ago</span>
                </div>
              </div>
              <div class="activity-item">
                <div class="activity-icon bg-green">
                  <i class="fas fa-check-circle"></i>
                </div>
                <div class="activity-details">
                  <p>Exam "Spring 2023 Final" completed by 15 students</p>
                  <span class="activity-time">1 hour ago</span>
                </div>
              </div>
              <div class="activity-item">
                <div class="activity-icon bg-orange">
                  <i class="fas fa-exclamation-circle"></i>
                </div>
                <div class="activity-details">
                  <p>System maintenance scheduled for tomorrow</p>
                  <span class="activity-time">3 hours ago</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="content-col">
          <div class="content-card">
            <div class="card-header">
              <h3>Upcoming Exams</h3>
              <a href="#" class="view-all">View All</a>
            </div>
            <div class="exam-list">
              <div class="exam-item">
                <div class="exam-date">
                  <span class="exam-day">15</span>
                  <span class="exam-month">Jun</span>
                </div>
                <div class="exam-details">
                  <h4>Midterm Exam - Mathematics</h4>
                  <p>10:00 AM - 12:00 PM</p>
                  <div class="exam-progress">
                    <div class="progress-bar" style="width: 65%;"></div>
                  </div>
                  <span class="progress-text">65% students registered</span>
                </div>
              </div>
              <div class="exam-item">
                <div class="exam-date">
                  <span class="exam-day">22</span>
                  <span class="exam-month">Jun</span>
                </div>
                <div class="exam-details">
                  <h4>Final Exam - Science</h4>
                  <p>09:00 AM - 11:30 AM</p>
                  <div class="exam-progress">
                    <div class="progress-bar" style="width: 42%;"></div>
                  </div>
                  <span class="progress-text">42% students registered</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Results -->
      <div class="content-card">
        <div class="card-header">
          <h3>Recent Results</h3>
          <a href="#" class="view-all">View All</a>
        </div>
        <div class="table-responsive">
          <table class="results-table">
            <thead>
            <tr>
              <th>Student</th>
              <th>Exam</th>
              <th>Date</th>
              <th>Score</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>
                <div class="student-info">
                  <img src="Dashboard/img/student1.jpg" alt="Student">
                  <span>Sarah Johnson</span>
                </div>
              </td>
              <td>Mathematics Midterm</td>
              <td>May 10, 2023</td>
              <td>92/100</td>
              <td><span class="status-passed">Passed</span></td>
              <td>
                <button class="action-btn view-btn">View</button>
                <button class="action-btn print-btn">Print</button>
              </td>
            </tr>
            <tr>
              <td>
                <div class="student-info">
                  <img src="Dashboard/img/student2.jpg" alt="Student">
                  <span>Michael Brown</span>
                </div>
              </td>
              <td>Science Final</td>
              <td>May 12, 2023</td>
              <td>78/100</td>
              <td><span class="status-passed">Passed</span></td>
              <td>
                <button class="action-btn view-btn">View</button>
                <button class="action-btn print-btn">Print</button>
              </td>
            </tr>
            <tr>
              <td>
                <div class="student-info">
                  <img src="Dashboard/img/student3.jpg" alt="Student">
                  <span>Emily Davis</span>
                </div>
              </td>
              <td>History Quiz</td>
              <td>May 15, 2023</td>
              <td>45/100</td>
              <td><span class="status-failed">Failed</span></td>
              <td>
                <button class="action-btn view-btn">View</button>
                <button class="action-btn print-btn">Print</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="Home/Js/Home.js"></script>

</body>
</html>