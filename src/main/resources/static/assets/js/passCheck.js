function check(input) {
    if (input.value !== document.getElementById('password').value) {
        input.setCustomValidity('Password Must be Matching.');
    } else {
        input.setCustomValidity('');
    }
}