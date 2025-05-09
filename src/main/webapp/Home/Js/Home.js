document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle functionality for mobile
    const sidebarToggle = document.createElement('div');
    sidebarToggle.className = 'sidebar-toggle';
    sidebarToggle.innerHTML = '<i class="fas fa-bars"></i>';
    document.querySelector('.top-bar').prepend(sidebarToggle);

    const sidebar = document.querySelector('.sidebar');
    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.toggle('collapsed');
    });

    // Notification dropdown
    const notificationIcon = document.querySelector('.notifications');
    const notificationDropdown = document.createElement('div');
    notificationDropdown.className = 'notification-dropdown';

    notificationIcon.appendChild(notificationDropdown);

    notificationIcon.addEventListener('click', function(e) {
        e.stopPropagation();
        notificationDropdown.classList.toggle('show');
    });

    // Close dropdown when clicking outside
    document.addEventListener('click', function() {
        notificationDropdown.classList.remove('show');
    });

    // Mark all as read functionality
    const markAllRead = notificationDropdown.querySelector('.mark-all-read');
    markAllRead.addEventListener('click', function() {
        const unreadItems = notificationDropdown.querySelectorAll('.unread');
        unreadItems.forEach(item => {
            item.classList.remove('unread');
        });
        document.querySelector('.notification-count').textContent = '0';
    });

    // Active sidebar link highlighting
    const sidebarLinks = document.querySelectorAll('.sidebar-nav a');
    sidebarLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            sidebarLinks.forEach(l => l.parentElement.classList.remove('active'));
            this.parentElement.classList.add('active');
        });
    });

    // Logout functionality
    const logoutBtn = document.querySelector('.logout-btn');
    logoutBtn.addEventListener('click', function(e) {
        e.preventDefault();
        // Here you would typically make an AJAX call to log out
        window.location.href = 'index.jsp';
    });

    // Print button functionality
    const printButtons = document.querySelectorAll('.print-btn');
    printButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Here you would implement print functionality
            alert('Print functionality would be implemented here');
        });
    });

    // View button functionality
    const viewButtons = document.querySelectorAll('.view-btn');
    viewButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Here you would implement view details functionality
            alert('View details functionality would be implemented here');
        });
    });
});