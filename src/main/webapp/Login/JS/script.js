// Login Form Handling
if (document.getElementById('loginForm')) {
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const rememberMe = document.getElementById('remember').checked;

        // Here you would typically send this data to a server
        console.log('Login attempt:', { username, password, rememberMe });

        // For demo purposes, just show an alert
        alert(`Login submitted for ${username}`);


    });
}

// Signup Form Handling
if (document.getElementById('signupForm')) {
    document.getElementById('signupForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            password: document.getElementById('password').value,
            confirmPassword: document.getElementById('confirmPassword').value
        };

        // Basic validation
        if (formData.password !== formData.confirmPassword) {
            alert("Passwords don't match!");
            return;
        }

        if (formData.password.length < 8) {
            alert("Password must be at least 8 characters long!");
            return;
        }

        // Here you would typically send this data to a server
        console.log('Signup attempt:', formData);

        // For demo purposes, just show an alert
        alert(`Account created for ${formData.firstName} ${formData.lastName}`);


    });
}