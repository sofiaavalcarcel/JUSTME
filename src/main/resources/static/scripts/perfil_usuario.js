document.addEventListener("DOMContentLoaded", function () {
  // Elementos del DOM
  const userProfile = document.getElementById("userProfile");
  const dropdownMenu = document.getElementById("dropdownMenu");
  const logoutBtn = document.getElementById("logoutBtn");
  const configBtn = document.getElementById("configBtn");
  const cancelBtn = document.getElementById("cancelBtn");
  const profileForm = document.getElementById("profileForm");
  const configForm = document.getElementById("configForm");
  const closeConfigBtn = document.getElementById("closeConfigBtn");
  const fileInput = document.getElementById("fileInput");
  const profileImageDisplay = document.getElementById("profileImageDisplay");
  const profileImage = document.getElementById("profileImage");
  const configModal = document.getElementById("configModal");

  // Datos iniciales del usuario
  const userData = {
    name: "María González",
    email: "maria@example.com",
    phone: "+57 310 123 4567",
    birthdate: "1990-05-15",
    address: "Calle 123 #45-67, Bogotá",
    about: "Soy una cliente frecuente de servicios de belleza, me encanta probar nuevos estilos y mantener mi apariencia fresca.",
    image: "https://randomuser.me/api/portraits/women/44.jpg",
    memberSince: "Enero 2023",
    servicesCompleted: 15,
    rating: 4.8,
    favorites: 3
  };

  // Configuración inicial
  const userConfig = {
    notifications: "all",
    language: "es",
    theme: "light",
    newsletter: true,
    promotions: true
  };

  // Mostrar/ocultar menú desplegable
  userProfile.addEventListener("click", function (e) {
    e.stopPropagation();
    this.classList.toggle("active");
    dropdownMenu.style.display = this.classList.contains("active")
      ? "block"
      : "none";
  });

  // Cerrar menú al hacer clic fuera
  document.addEventListener("click", function () {
    userProfile.classList.remove("active");
    dropdownMenu.style.display = "none";
  });

  // Cerrar sesión
  logoutBtn.addEventListener("click", function (e) {
    e.preventDefault();
    Swal.fire({
      title: '¿Cerrar sesión?',
      text: '¿Estás seguro que deseas cerrar sesión?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#e16bff',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Sí, cerrar sesión',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        window.location.href = "principal.html";
      }
    });
  });

  // Mostrar modal de configuración
  configBtn.addEventListener("click", function(e) {
    e.preventDefault();
    configModal.classList.remove("oculto");
    configModal.classList.add("mostrar");
    
    // Cargar configuración actual
    document.getElementById("notifications").value = userConfig.notifications;
    document.getElementById("language").value = userConfig.language;
    document.getElementById("theme").value = userConfig.theme;
    document.getElementById("newsletter").checked = userConfig.newsletter;
    document.getElementById("promotions").checked = userConfig.promotions;
  });

  // Cerrar modal de configuración
  closeConfigBtn.addEventListener("click", function() {
    configModal.classList.remove("mostrar");
    configModal.classList.add("oculto");
  });

  // Cerrar modal al hacer clic fuera
  window.addEventListener("click", function(event) {
    if (event.target === configModal) {
      configModal.classList.remove("mostrar");
      configModal.classList.add("oculto");
    }
  });

  // Cambiar imagen de perfil
  fileInput.addEventListener("change", function(e) {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(event) {
        profileImageDisplay.src = event.target.result;
        profileImage.src = event.target.result;
        
        // Mostrar notificación de éxito
        Swal.fire({
          title: '¡Foto actualizada!',
          text: 'Tu foto de perfil se ha cambiado correctamente',
          icon: 'success',
          confirmButtonColor: '#e16bff'
        });
      };
      reader.readAsDataURL(file);
    }
  });

  // Cancelar cambios en el perfil
  cancelBtn.addEventListener("click", function() {
    // Restaurar valores originales
    document.getElementById("fullName").value = userData.name;
    document.getElementById("email").value = userData.email;
    document.getElementById("phone").value = userData.phone;
    document.getElementById("birthdate").value = userData.birthdate;
    document.getElementById("address").value = userData.address;
    document.getElementById("about").value = userData.about;
    
    Swal.fire({
      title: 'Cambios cancelados',
      text: 'No se han guardado los cambios',
      icon: 'info',
      confirmButtonColor: '#e16bff'
    });
  });

  // Guardar cambios en el perfil
  profileForm.addEventListener("submit", function(e) {
    e.preventDefault();
    
    // Actualizar datos del usuario (en un caso real, aquí iría una llamada API)
    userData.name = document.getElementById("fullName").value;
    userData.email = document.getElementById("email").value;
    userData.phone = document.getElementById("phone").value;
    userData.birthdate = document.getElementById("birthdate").value;
    userData.address = document.getElementById("address").value;
    userData.about = document.getElementById("about").value;
    
    // Actualizar nombre en el header
    document.querySelector(".user-profile span").textContent = userData.name;
    
    Swal.fire({
      title: '¡Perfil actualizado!',
      text: 'Tus cambios se han guardado correctamente',
      icon: 'success',
      confirmButtonColor: '#e16bff'
    });
  });

  // Guardar configuración
  configForm.addEventListener("submit", function(e) {
    e.preventDefault();
    
    // Actualizar configuración
    userConfig.notifications = document.getElementById("notifications").value;
    userConfig.language = document.getElementById("language").value;
    userConfig.theme = document.getElementById("theme").value;
    userConfig.newsletter = document.getElementById("newsletter").checked;
    userConfig.promotions = document.getElementById("promotions").checked;
    
    // Cerrar modal
    configModal.classList.remove("mostrar");
    configModal.classList.add("oculto");
    
    Swal.fire({
      title: 'Configuración guardada',
      text: 'Tus preferencias se han actualizado',
      icon: 'success',
      confirmButtonColor: '#e16bff'
    });
  });
});