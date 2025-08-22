function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function showFieldError(fieldId, message) {
    const errorDiv = document.getElementById(fieldId + 'Error');
    const successDiv = document.getElementById(fieldId + 'Success');
    const input = document.getElementById(fieldId);
    
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
    successDiv.style.display = 'none';
    input.classList.remove('success');
    input.classList.add('error');
}

function showFieldSuccess(fieldId) {
    const errorDiv = document.getElementById(fieldId + 'Error');
    const successDiv = document.getElementById(fieldId + 'Success');
    const input = document.getElementById(fieldId);
    
    errorDiv.style.display = 'none';
    successDiv.style.display = 'block';
    input.classList.remove('error');
    input.classList.add('success');
}

function showResetSection() {
    document.getElementById('resetSection').style.display = 'block';
    document.getElementById('resetEmail').focus();
}

function hideResetSection() {
    document.getElementById('resetSection').style.display = 'none';
    document.getElementById('resetEmail').value = '';
    document.getElementById('resetEmail').classList.remove('error', 'success');
    document.getElementById('resetEmailError').style.display = 'none';
    document.getElementById('resetEmailSuccess').style.display = 'none';
}

document.getElementById('resetEmail').addEventListener('blur', function() {
    const email = this.value.trim();
    if (email) {
        if (validateEmail(email)) {
            showFieldSuccess('resetEmail');
        } else {
            showFieldError('resetEmail', 'Invalid email');
        }
    }
});

function executeReset() {
    const email = document.getElementById('resetEmail').value.trim();
    
    if (!email) {
        showFieldError('resetEmail', 'Please enter your email to reset your password');
        return;
    }
    
    if (!validateEmail(email)) {
        showFieldError('resetEmail', 'Invalid email');
        return;
    }
    
    if (confirm('Are you sure you want to reset your password?')) {
        const resetBtn = document.getElementById('resetBtn');
        resetBtn.disabled = true;
        resetBtn.textContent = 'Processing...';
        
        const url = `/auth/reset-password?email=${encodeURIComponent(email)}`;
        
        fetch(url, {
            method: 'POST'
        })
        .then(response => response.text())
        .then(data => {
            if (data.includes('Successful')) {
                showMessage('Password reset successful! Please check your email', 'success');
                hideResetSection();
            } else {
                alert('Error: ' + data);
            }
        })
        .catch(error => {
            alert('Connection error: ' + error.message);
        })
        .finally(() => {
            resetBtn.disabled = false;
            resetBtn.textContent = 'Reset Password';
        });
    }
}

function showMessage(message, type) {
    const successDiv = document.getElementById('successMessage');
    successDiv.textContent = message;
    successDiv.style.display = 'block';
    
    setTimeout(() => {
        successDiv.style.display = 'none';
    }, 5000);
}