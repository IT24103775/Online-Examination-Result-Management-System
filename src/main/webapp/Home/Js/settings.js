document.addEventListener('DOMContentLoaded', function() {
    // Tab functionality
    function openTab(evt, tabName) {
        const tabContents = document.getElementsByClassName('tab-content');
        for (let i = 0; i < tabContents.length; i++) {
            tabContents[i].style.display = 'none';
        }

        const tabButtons = document.getElementsByClassName('tab-button');
        for (let i = 0; i < tabButtons.length; i++) {
            tabButtons[i].className = tabButtons[i].className.replace(' active', '');
        }

        document.getElementById(tabName).style.display = 'block';
        evt.currentTarget.className += ' active';
    }

    // Font size slider
    const fontSizeSlider = document.getElementById('fontSize');
    const fontSizeValue = document.getElementById('fontSizeValue');

    if (fontSizeSlider && fontSizeValue) {
        fontSizeSlider.addEventListener('input', function() {
            fontSizeValue.textContent = this.value + 'px';
        });
    }

    // Password strength meter
    const newPassword = document.getElementById('newPassword');
    if (newPassword) {
        newPassword.addEventListener('input', function() {
            const strengthBar = document.getElementById('strengthBar');
            const strengthText = document.getElementById('strengthText');
            const password = this.value;
            let strength = 0;

            // Check password length
            if (password.length >= 8) strength += 1;
            if (password.length >= 12) strength += 1;

            // Check for mixed case
            if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) strength += 1;

            // Check for numbers
            if (password.match(/([0-9])/)) strength += 1;

            // Check for special chars
            if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1;

            // Update UI
            switch(strength) {
                case 0:
                    strengthBar.style.width = '0%';
                    strengthBar.style.backgroundColor = '#e74c3c';
                    strengthText.textContent = 'Password strength: Very Weak';
                    break;
                case 1:
                    strengthBar.style.width = '25%';
                    strengthBar.style.backgroundColor = '#e74c3c';
                    strengthText.textContent = 'Password strength: Weak';
                    break;
                case 2:
                    strengthBar.style.width = '50%';
                    strengthBar.style.backgroundColor = '#f39c12';
                    strengthText.textContent = 'Password strength: Moderate';
                    break;
                case 3:
                    strengthBar.style.width = '75%';
                    strengthBar.style.backgroundColor = '#f39c12';
                    strengthText.textContent = 'Password strength: Good';
                    break;
                case 4:
                case 5:
                    strengthBar.style.width = '100%';
                    strengthBar.style.backgroundColor = '#2ecc71';
                    strengthText.textContent = 'Password strength: Strong';
                    break;
            }
        });
    }

    // Toggle password visibility
    window.togglePassword = function(inputId) {
        const input = document.getElementById(inputId);
        const icon = input.nextElementSibling;

        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    }

    // Form submissions with AJAX
    const forms = {
        accountForm: '/UpdateAccount',
        notificationForm: '/UpdateNotifications',
        securityForm: '/UpdatePassword',
        appearanceForm: '/UpdateAppearance'
    };

    Object.keys(forms).forEach(formId => {
        const form = document.getElementById(formId);
        if (form) {
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                const formData = new FormData(this);
                const messageElement = document.getElementById(formId.replace('Form', 'Message'));

                fetch(forms[formId], {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            showMessage(messageElement, data.message, 'success');
                            if (formId === 'appearanceForm') {
                                // Update theme if appearance changed
                                updateTheme(data.theme);
                            }
                        } else {
                            showMessage(messageElement, data.message, 'error');
                        }
                    })
                    .catch(error => {
                        showMessage(messageElement, 'An error occurred. Please try again.', 'error');
                        console.error('Error:', error);
                    });
            });
        }
    });

    function showMessage(element, message, type) {
        element.textContent = message;
        element.className = 'message ' + type;
        setTimeout(() => {
            element.style.opacity = '1';
        }, 100);

        // Hide message after 5 seconds
        setTimeout(() => {
            element.style.opacity = '0';
            setTimeout(() => {
                element.className = 'message';
            }, 300);
        }, 5000);
    }

    function updateTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
    }

    // Initialize theme
    const savedTheme = localStorage.getItem('theme') || 'light';
    updateTheme(savedTheme);

    // Check for URL parameters to show messages
    const urlParams = new URLSearchParams(window.location.search);
    const successMessage = urlParams.get('success');
    const errorMessage = urlParams.get('error');

    if (successMessage) {
        const messageElement = document.createElement('div');
        messageElement.className = 'message success';
        messageElement.textContent = decodeURIComponent(successMessage);
        document.body.prepend(messageElement);

        setTimeout(() => {
            messageElement.remove();
        }, 5000);
    }

    if (errorMessage) {
        const messageElement = document.createElement('div');
        messageElement.className = 'message error';
        messageElement.textContent = decodeURIComponent(errorMessage);
        document.body.prepend(messageElement);

        setTimeout(() => {
            messageElement.remove();
        }, 5000);
    }
});