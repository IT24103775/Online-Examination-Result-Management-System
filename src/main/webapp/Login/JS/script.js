
    document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");

    form.addEventListener("submit", function (event) {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const rememberCheckbox = document.getElementById("remember");

    if (!username || !password) {
    alert("Please fill in both username and password.");
    event.preventDefault(); // Stop form submission
    return;
}

    // If 'remember' is not checked, explicitly set it to false
    if (!rememberCheckbox.checked) {
    // Create a hidden input field to send 'false'
    let hiddenInput = document.createElement("input");
    hiddenInput.type = "hidden";
    hiddenInput.name = "remember";
    hiddenInput.value = "false";
    form.appendChild(hiddenInput);
}
});
});
