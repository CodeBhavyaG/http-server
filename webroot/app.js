document.addEventListener('DOMContentLoaded', function() {
  const form = document.querySelector('#demoForm');
  const greeting = document.querySelector('#greeting');

  form.addEventListener('submit', function(event) {
    event.preventDefault();
    const name = document.querySelector('#name').value.trim();

    if (name.length === 0) {
      greeting.textContent = 'Please enter your name.';
      return;
    }
    greeting.textContent = 'Hello, ' + name + '! Your static Java web app is working.';
  });
});