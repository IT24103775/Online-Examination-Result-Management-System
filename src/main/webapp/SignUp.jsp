<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up</title>
  <link rel="stylesheet" href="Login/css/style.css">
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
</head>
<body>
<div class="auth-container">
  <div class="signup-container" id="signupContainer">
    <h1>Sign Up</h1>

    <form id="signupForm"  method="post" action="signup">
      <div class="name-group">
        <div class="input-group half-width">
          <label for="firstName">First Name</label>
          <input type="text" id="firstName" name="firstName" required>
        </div>

        <div class="input-group half-width">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" required>
        </div>
      </div>

      <div class="input-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>
      </div>

      <div class="input-group">
        <label for="phone">Contact Number</label>
        <input type="tel" id="phone" name="phone" required>
      </div>

      <div class="input-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
        <div class="password-hint">Use 8 or more characters with a mix of letters, numbers & symbols</div>
      </div>

      <div class="input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required>
      </div>

      <button type="submit" class="auth-button">Sign Up</button>
    </form>

    <div class="switch-auth">
      Already have an account? <a href="index.jsp" class="switch-link">Login</a>
    </div>
  </div>
</div>

<script src=""></script>
</body>
</html>