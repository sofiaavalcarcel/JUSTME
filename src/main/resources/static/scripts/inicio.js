// ✅ Mostrar/Ocultar el menú desplegable del usuario
document.addEventListener("DOMContentLoaded", function () {
  const userProfile = document.getElementById("userProfile");
  const dropdownMenu = document.getElementById("dropdownMenu");

  if (userProfile && dropdownMenu) {
    userProfile.addEventListener("click", () => {
      dropdownMenu.classList.toggle("active"); // Activa/desactiva la clase
    });
  }
});




