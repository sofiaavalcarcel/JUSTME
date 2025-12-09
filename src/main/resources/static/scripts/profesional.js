document.addEventListener("DOMContentLoaded", function () {
    // =======================
    // MENÚ HAMBURGUESA
    // =======================
    const hamburgerBtn = document.getElementById("hamburgerBtn");
    const mainNav = document.getElementById("mainNav");
    const body = document.body;

    if (hamburgerBtn && mainNav) {
        hamburgerBtn.addEventListener("click", function (e) {
            e.stopPropagation();
            e.preventDefault();
            
            const isOpening = !mainNav.classList.contains("active");
            
            // Cerrar dropdown del usuario si está abierto
            if (userProfile && userProfile.classList.contains("active")) {
                userProfile.classList.remove("active");
                if (dropdownMenu) {
                    dropdownMenu.style.display = "none";
                }
            }
            
            // Alternar menú hamburguesa
            mainNav.classList.toggle("active");
            
            // Cambiar icono
            const icon = this.querySelector("i");
            if (mainNav.classList.contains("active")) {
                icon.classList.remove("fa-bars");
                icon.classList.add("fa-times");
                // Prevenir scroll del body cuando el menú está abierto
                body.style.overflow = 'hidden';
            } else {
                icon.classList.remove("fa-times");
                icon.classList.add("fa-bars");
                body.style.overflow = '';
            }
        });

        // Cerrar menú al hacer clic en un enlace
        const navLinks = mainNav.querySelectorAll("a");
        navLinks.forEach(link => {
            link.addEventListener("click", function () {
                if (mainNav.classList.contains("active")) {
                    mainNav.classList.remove("active");
                    body.style.overflow = '';
                    const icon = hamburgerBtn.querySelector("i");
                    if (icon) {
                        icon.classList.remove("fa-times");
                        icon.classList.add("fa-bars");
                    }
                }
            });
        });

        // Cerrar menú al hacer clic fuera (solo en móvil)
        document.addEventListener("click", function (e) {
            if (window.innerWidth <= 992) {
                const isClickInsideNav = mainNav.contains(e.target);
                const isClickOnHamburger = hamburgerBtn.contains(e.target);
                
                if (!isClickInsideNav && !isClickOnHamburger && mainNav.classList.contains("active")) {
                    mainNav.classList.remove("active");
                    body.style.overflow = '';
                    const icon = hamburgerBtn.querySelector("i");
                    if (icon) {
                        icon.classList.remove("fa-times");
                        icon.classList.add("fa-bars");
                    }
                }
            }
        });
    }

    // =======================
    // MENU DESPLEGABLE USUARIO
    // =======================
    const userProfile = document.getElementById("userProfile");
    const dropdownMenu = document.getElementById("dropdownMenu");
    const logoutBtn = document.getElementById("logoutBtn");

    if (userProfile && dropdownMenu) {
        userProfile.addEventListener("click", function (e) {
            e.stopPropagation();
            e.preventDefault();
            
            // Cerrar menú hamburguesa si está abierto
            if (hamburgerBtn && mainNav && mainNav.classList.contains("active")) {
                mainNav.classList.remove("active");
                body.style.overflow = '';
                const icon = hamburgerBtn.querySelector("i");
                if (icon) {
                    icon.classList.remove("fa-times");
                    icon.classList.add("fa-bars");
                }
            }
            
            // Alternar dropdown del usuario
            this.classList.toggle("active");
            
            if (this.classList.contains("active")) {
                dropdownMenu.style.display = "block";
                // Asegurar que esté visible
                setTimeout(() => {
                    dropdownMenu.style.opacity = "1";
                    dropdownMenu.style.visibility = "visible";
                    dropdownMenu.style.transform = "translateY(0)";
                }, 10);
            } else {
                dropdownMenu.style.opacity = "0";
                dropdownMenu.style.visibility = "hidden";
                dropdownMenu.style.transform = "translateY(10px)";
                setTimeout(() => {
                    dropdownMenu.style.display = "none";
                }, 300);
            }
        });

        // Cerrar dropdown al hacer clic fuera
        document.addEventListener("click", function (e) {
            if (userProfile && dropdownMenu) {
                const isClickInsideDropdown = dropdownMenu.contains(e.target);
                const isClickOnUserProfile = userProfile.contains(e.target);
                
                if (!isClickInsideDropdown && !isClickOnUserProfile && userProfile.classList.contains("active")) {
                    userProfile.classList.remove("active");
                    dropdownMenu.style.opacity = "0";
                    dropdownMenu.style.visibility = "hidden";
                    dropdownMenu.style.transform = "translateY(10px)";
                    setTimeout(() => {
                        dropdownMenu.style.display = "none";
                    }, 300);
                }
            }
        });

        // Evitar que los clics dentro del dropdown lo cierren
        dropdownMenu.addEventListener("click", function (e) {
            e.stopPropagation();
        });
    }

    // =======================
    // LOGOUT
    // =======================
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();
            if (confirm("¿Estás seguro que deseas cerrar sesión?")) {
                window.location.href = "principal.html";
            }
        });
    }

    // =======================
    // MANEJO DE FORMULARIO DE SERVICIO
    // =======================
    const addServiceBtn = document.getElementById("addServiceBtn");
    const addServiceSection = document.getElementById("addServiceSection");
    const cancelServiceBtn = document.getElementById("cancelServiceBtn");
    const addServiceForm = document.getElementById("addServiceForm");

    if (addServiceBtn && addServiceSection) {
        addServiceBtn.addEventListener("click", function () {
            addServiceSection.style.display = "block";
            addServiceBtn.style.display = "none";
            // Scroll suave al formulario
            addServiceSection.scrollIntoView({ behavior: 'smooth' });
        });
    }

    if (cancelServiceBtn && addServiceSection && addServiceBtn) {
        cancelServiceBtn.addEventListener("click", function () {
            addServiceSection.style.display = "none";
            addServiceBtn.style.display = "flex";
            // Resetear formulario
            if (addServiceForm) {
                addServiceForm.reset();
            }
        });
    }

    if (addServiceForm) {
        addServiceForm.addEventListener("submit", function (e) {
            e.preventDefault();
            
            // Obtener valores del formulario
            const serviceName = document.getElementById("service-name").value;
            const serviceCategory = document.getElementById("service-category").value;
            const serviceDuration = document.getElementById("service-duration").value;
            const servicePrice = document.getElementById("service-price").value;
            const serviceDescription = document.getElementById("service-description").value;
            
            // Validar campos
            if (!serviceName || !serviceCategory || !serviceDuration || !servicePrice || !serviceDescription) {
                alert("Por favor, complete todos los campos del formulario.");
                return;
            }
            
            // Mostrar mensaje de éxito
            alert("Servicio agregado exitosamente!");
            
            // Ocultar formulario y mostrar botón
            addServiceSection.style.display = "none";
            addServiceBtn.style.display = "flex";
            
            // Resetear formulario
            addServiceForm.reset();
            
            // Aquí normalmente enviarías los datos al servidor
            console.log("Nuevo servicio:", {
                name: serviceName,
                category: serviceCategory,
                duration: serviceDuration,
                price: servicePrice,
                description: serviceDescription
            });
        });
    }

    // =======================
    // MANEJO DE BOTONES DE CARDS
    // =======================
    // Botones de editar en servicios
    const editServiceBtns = document.querySelectorAll('.service-card .btn-edit');
    editServiceBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const serviceCard = this.closest('.service-card');
            const serviceName = serviceCard.querySelector('h4').textContent;
            alert(`Editando servicio: ${serviceName}`);
            // Aquí normalmente abrirías un modal o formulario de edición
        });
    });

    // Botones de eliminar en servicios
    const deleteServiceForms = document.querySelectorAll('.service-card .delete-form');
    deleteServiceForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            const serviceCard = this.closest('.service-card');
            const serviceName = serviceCard.querySelector('h4').textContent;
            
            if (confirm(`¿Estás seguro de eliminar el servicio "${serviceName}"?`)) {
                serviceCard.style.opacity = '0.5';
                serviceCard.style.transform = 'scale(0.95)';
                
                // Simular eliminación después de 0.5 segundos
                setTimeout(() => {
                    serviceCard.remove();
                    alert('Servicio eliminado exitosamente!');
                    
                    // Actualizar contador si es necesario
                    updateServicesCount();
                }, 500);
            }
        });
    });

    // Botones de editar en citas
    const editAppointmentBtns = document.querySelectorAll('.cita-card .btn-edit');
    editAppointmentBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const citaCard = this.closest('.cita-card');
            const clientName = citaCard.querySelector('.card-content p strong:first-child + span').textContent;
            alert(`Editando cita de: ${clientName}`);
            // Aquí normalmente abrirías un modal o formulario de edición
        });
    });

    // Botones de eliminar en citas
    const deleteAppointmentBtns = document.querySelectorAll('.cita-card .btn-delete');
    deleteAppointmentBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const citaCard = this.closest('.cita-card');
            const clientName = citaCard.querySelector('.card-content p strong:first-child + span').textContent;
            
            if (confirm(`¿Estás seguro de eliminar la cita de "${clientName}"?`)) {
                citaCard.style.opacity = '0.5';
                citaCard.style.transform = 'scale(0.95)';
                
                // Simular eliminación después de 0.5 segundos
                setTimeout(() => {
                    citaCard.remove();
                    alert('Cita eliminada exitosamente!');
                    
                    // Actualizar contador si es necesario
                    updateAppointmentsCount();
                }, 500);
            }
        });
    });

    // =======================
    // FUNCIONES AUXILIARES
    // =======================
    function updateServicesCount() {
        const servicesCount = document.querySelectorAll('.service-card').length;
        console.log(`Total servicios: ${servicesCount}`);
        // Aquí podrías actualizar algún contador en la UI si lo tienes
    }

    function updateAppointmentsCount() {
        const appointmentsCount = document.querySelectorAll('.cita-card').length;
        console.log(`Total citas: ${appointmentsCount}`);
        // Aquí podrías actualizar algún contador en la UI si lo tienes
    }

    // =======================
    // AJUSTES RESPONSIVE PARA CARDS
    // =======================
    function adjustCardsLayout() {
        const cardsContainers = document.querySelectorAll('.cards-container, .citas-container');
        const isMobile = window.innerWidth <= 768;
        
        cardsContainers.forEach(container => {
            const cards = container.querySelectorAll('.service-card, .cita-card');
            
            // Ajustar grid en móvil
            if (isMobile) {
                container.style.gridTemplateColumns = '1fr';
                
                // Centrar si solo hay una card
                if (cards.length === 1) {
                    container.style.display = 'flex';
                    container.style.justifyContent = 'center';
                    container.style.alignItems = 'flex-start';
                } else {
                    container.style.display = 'grid';
                }
                
                // Ajustar padding en cards para móvil pequeño
                if (window.innerWidth <= 400) {
                    cards.forEach(card => {
                        card.style.padding = '15px 12px';
                    });
                }
            } else {
                // Restaurar grid en escritorio
                if (container.classList.contains('cards-container')) {
                    container.style.gridTemplateColumns = 'repeat(auto-fit, minmax(300px, 1fr))';
                } else if (container.classList.contains('citas-container')) {
                    container.style.gridTemplateColumns = 'repeat(auto-fit, minmax(320px, 1fr))';
                }
                container.style.display = 'grid';
                container.style.justifyContent = '';
                container.style.alignItems = '';
                
                // Restaurar padding
                cards.forEach(card => {
                    card.style.padding = '';
                });
            }
        });
    }

    // Ajustar layout al cargar y cambiar tamaño
    window.addEventListener('load', adjustCardsLayout);
    window.addEventListener('resize', adjustCardsLayout);
    adjustCardsLayout();
});
