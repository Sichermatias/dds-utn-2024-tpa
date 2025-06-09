// Toggle between light and dark modes using local storage
window.addEventListener('DOMContentLoaded', () => {
  const toggle = document.getElementById('themeToggle');
  if(!toggle) return;
  const body = document.body;
  const saved = localStorage.getItem('theme');
  if (saved === 'dark') {
    body.classList.add('dark-mode');
    toggle.checked = true;
  }
  toggle.addEventListener('change', () => {
    body.classList.toggle('dark-mode');
    if (body.classList.contains('dark-mode')) {
      localStorage.setItem('theme', 'dark');
    } else {
      localStorage.setItem('theme', 'light');
    }
  });
});
