const form = document.getElementById('registerForm');
const submitBtn = document.getElementById('submitBtn');

// Validation functions
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword(password) {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumbers = /\d/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    
    if (password.length < minLength) return { valid: false, strength: 'weak', message: 'Password is too short' };
    if (!hasUpperCase || !hasLowerCase || !hasNumbers || !hasSpecialChar) return { valid: false, strength: 'fair', message: 'Missing required characters' };
    if (password.length >= 12) return { valid: true, strength: 'strong', message: 'Very strong password' };
    return { valid: true, strength: 'good', message: 'Strong password' };
}

function validateName(name) {
    const nameRegex = /^[a-zA-ZÀ-ỹ\s]{2,50}$/;
    return nameRegex.test(name);
}

function validateAddress(address) {
    return address.length >= 5;
}

function validatePhone(phone) {
    const phoneRegex = /^[0-9]{10,11}$/;
    return phoneRegex.test(phone);
}

function updatePasswordStrength(password) {
    const strengthBar = document.getElementById('passwordStrengthBar');
    const result = validatePassword(password);
    
    strengthBar.className = 'password-strength-bar strength-' + result.strength;
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

function validateField(fieldId, value, validator, errorMessage) {
    if (validator(value)) {
        showFieldSuccess(fieldId);
        return true;
    } else {
        showFieldError(fieldId, errorMessage);
        return false;
    }
}

// Event listeners
document.getElementById('email').addEventListener('blur', function() {
    const email = this.value.trim();
    if (email) {
        validateField('email', email, validateEmail, 'Invalid email');
    }
});

document.getElementById('password').addEventListener('input', function() {
    const password = this.value;
    if (password) {
        updatePasswordStrength(password);
        validateField('password', password, (pwd) => validatePassword(pwd).valid, 'Password is not strong enough');
    }
});

document.getElementById('confirmPassword').addEventListener('blur', function() {
    const password = document.getElementById('password').value;
    const confirmPassword = this.value;
    if (confirmPassword) {
        if (password === confirmPassword) {
            showFieldSuccess('confirmPassword');
        } else {
            showFieldError('confirmPassword', 'Confirmation password does not match');
        }
    }
});

document.getElementById('name').addEventListener('blur', function() {
    const name = this.value.trim();
    if (name) {
        validateField('name', name, validateName, 'Invalid full name');
    }
});

document.getElementById('address').addEventListener('blur', function() {
    const address = this.value.trim();
    if (address) {
        validateField('address', address, validateAddress, 'Address must have at least 5 characters');
    }
});

document.getElementById('phone').addEventListener('blur', function() {
    const phone = this.value.trim();
    if (phone) {
        validateField('phone', phone, validatePhone, 'Invalid phone number');
    }
});

// Form submission
form.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const name = document.getElementById('name').value.trim();
    const address = document.getElementById('address').value.trim();
    const phone = document.getElementById('phone').value.trim();
    
    // Validate all fields
    const isEmailValid = validateField('email', email, validateEmail, 'Invalid email');
    const isPasswordValid = validateField('password', password, (pwd) => validatePassword(pwd).valid, 'Password is not strong enough');
    const isNameValid = validateField('name', name, validateName, 'Invalid full name');
    const isAddressValid = validateField('address', address, validateAddress, 'Address must have at least 5 characters');
    const isPhoneValid = validateField('phone', phone, validatePhone, 'Invalid phone number');
    
    if (password !== confirmPassword) {
        showFieldError('confirmPassword', 'Confirmation password does not match');
        return;
    }
    
    if (isEmailValid && isPasswordValid && isNameValid && isAddressValid && isPhoneValid) {
        submitBtn.disabled = true;
        submitBtn.textContent = 'Registering...';
        
        const userData = {
            email: email,
            password: password,
            name: name,
            address: address,
            phone: phone
        };
        
        fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        })
        .then(response => response.text())
        .then(data => {
            if (data.includes('successful')) {
                showMessage('Registration successful! You can now log in.', 'success');
                form.reset();
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
            } else {
                showMessage(data, 'error');
            }
        })
        .catch(error => {
            showMessage('Connection error: ' + error.message, 'error');
        })
        .finally(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Register';
        });
    }
});

function showMessage(message, type) {
    const errorDiv = document.getElementById('errorMessage');
    const successDiv = document.getElementById('successMessage');
    
    if (type === 'error') {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        successDiv.style.display = 'none';
    } else {
        successDiv.textContent = message;
        successDiv.style.display = 'block';
        errorDiv.style.display = 'none';
    }
}
