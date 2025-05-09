document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const addUserBtn = document.getElementById('addUserBtn');
    const userModal = document.getElementById('userModal');
    const deleteModal = document.getElementById('deleteModal');
    const closeModal = document.querySelectorAll('.close');
    const cancelBtn = document.getElementById('cancelBtn');
    const userForm = document.getElementById('userForm');
    const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    const userTable = document.getElementById('userTable');

    // Open Add User Modal
    addUserBtn.addEventListener('click', function() {
        document.getElementById('modalTitle').textContent = 'Add New User';
        userForm.reset();
        document.getElementById('userId').value = '';
        userModal.style.display = 'block';
    });

    // Close Modals
    function closeModals() {
        userModal.style.display = 'none';
        deleteModal.style.display = 'none';
    }

    closeModal.forEach(btn => btn.addEventListener('click', closeModals));
    cancelBtn.addEventListener('click', closeModals);
    cancelDeleteBtn.addEventListener('click', closeModals);

    // Click outside modal to close
    window.addEventListener('click', function(event) {
        if (event.target === userModal || event.target === deleteModal) {
            closeModals();
        }
    });

    // Edit User - Event delegation for dynamically added buttons
    userTable.addEventListener('click', function(e) {
        if (e.target.classList.contains('btn-edit')) {
            const row = e.target.closest('tr');
            const cells = row.cells;

            document.getElementById('modalTitle').textContent = 'Edit User';
            document.getElementById('userId').value = cells[0].textContent;
            document.getElementById('firstName').value = cells[1].textContent;
            document.getElementById('lastName').value = cells[2].textContent;
            document.getElementById('email').value = cells[3].textContent;
            document.getElementById('role').value = cells[4].textContent;
            document.getElementById('status').value = cells[5].querySelector('.status').textContent;

            userModal.style.display = 'block';
        }

        // Delete User
        if (e.target.classList.contains('btn-delete')) {
            const row = e.target.closest('tr');
            document.getElementById('deleteUserId').value = row.cells[0].textContent;
            deleteModal.style.display = 'block';
        }
    });

    // Form Submission
    userForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const userId = document.getElementById('userId').value;
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const email = document.getElementById('email').value;
        const role = document.getElementById('role').value;
        const status = document.getElementById('status').value;

        // In a real application, you would send this data to the server
        console.log('User data to save:', {
            userId, firstName, lastName, email, role, status
        });

        // For demo purposes, we'll update the table directly
        if (userId) {
            // Edit existing user
            const rows = userTable.getElementsByTagName('tr');
            for (let i = 1; i < rows.length; i++) { // Skip header row
                if (rows[i].cells[0].textContent === userId) {
                    rows[i].cells[1].textContent = firstName;
                    rows[i].cells[2].textContent = lastName;
                    rows[i].cells[3].textContent = email;
                    rows[i].cells[4].textContent = role;

                    const statusSpan = rows[i].cells[5].querySelector('.status');
                    statusSpan.textContent = status;
                    statusSpan.className = 'status ' + status.toLowerCase();
                    break;
                }
            }
        } else {
            // Add new user - in a real app, the ID would come from the server
            const newId = parseInt(userTable.rows[userTable.rows.length - 1].cells[0].textContent) + 1;
            const newRow = userTable.insertRow();

            newRow.innerHTML = `
                <td>${newId}</td>
                <td>${firstName}</td>
                <td>${lastName}</td>
                <td>${email}</td>
                <td>${role}</td>
                <td><span class="status ${status.toLowerCase()}">${status}</span></td>
                <td>
                    <button class="btn btn-edit">Edit</button>
                    <button class="btn btn-delete">Delete</button>
                </td>
            `;
        }

    });

    // Confirm Delete
    confirmDeleteBtn.addEventListener('click', function() {
        const userId = document.getElementById('deleteUserId').value;

        // In a real application, you would send a delete request to the server
        console.log('Deleting user with ID:', userId);

        // For demo purposes, we'll remove the row directly
        const rows = userTable.getElementsByTagName('tr');
        for (let i = 1; i < rows.length; i++) { // Skip header row
            if (rows[i].cells[0].textContent === userId) {
                userTable.deleteRow(i);
                break;
            }
        }

    });
});