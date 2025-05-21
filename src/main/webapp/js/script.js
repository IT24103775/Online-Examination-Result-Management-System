// Navigation functionality for dashboard panels
document.addEventListener('DOMContentLoaded', function() {
    // Handle navigation menu clicks
    const navItems = document.querySelectorAll('.nav-menu a');
    
    navItems.forEach(function(item) {
        item.addEventListener('click', function(e) {
            // Only if the href is a fragment
            if (this.getAttribute('href').startsWith('#')) {
                e.preventDefault();
                
                // Remove active class from all nav items
                navItems.forEach(function(navItem) {
                    navItem.parentElement.classList.remove('active');
                });
                
                // Add active class to current nav item
                this.parentElement.classList.add('active');
                
                // Hide all sections
                document.querySelectorAll('.panel-section').forEach(function(section) {
                    section.style.display = 'none';
                });
                
                // Show target section
                const targetId = this.getAttribute('href').substring(1);
                document.getElementById(targetId).style.display = 'block';
            }
        });
    });
    
    // Set active menu based on hash in URL or default to first item
    const setActiveMenu = () => {
        let hash = window.location.hash;
        if (!hash) {
            // Default to first menu item
            const firstNavItem = document.querySelector('.nav-menu li:first-child a');
            if (firstNavItem) {
                hash = firstNavItem.getAttribute('href');
            }
        }
        
        if (hash) {
            const targetNavItem = document.querySelector(`.nav-menu a[href="${hash}"]`);
            if (targetNavItem) {
                targetNavItem.click();
            } else {
                // If hash doesn't match any nav item, default to first
                const firstNavItem = document.querySelector('.nav-menu li:first-child a');
                if (firstNavItem) {
                    firstNavItem.click();
                }
            }
        }
    };
    
    // Call on initial load
    setActiveMenu();
    
    // Call when hash changes
    window.addEventListener('hashchange', setActiveMenu);
    
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

// Modal functionality for user management
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Close modals when clicking outside
window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
};
