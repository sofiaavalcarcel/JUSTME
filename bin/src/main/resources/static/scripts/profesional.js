document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const userProfile = document.getElementById('userProfile');
    const dropdownMenu = document.getElementById('dropdownMenu');
    const addServiceBtn = document.getElementById('addServiceBtn');
    const addServiceSection = document.getElementById('addServiceSection');
    const cancelServiceBtn = document.getElementById('cancelServiceBtn');
    const addServiceForm = document.getElementById('addServiceForm');
    const servicesList = document.querySelector('.services-list');
    const appointmentsList = document.querySelector('.appointments-list');
    const reviewsList = document.querySelector('.reviews-list');
    const switchToClientBtn = document.getElementById('switchToClient');
    const logoutBtn = document.getElementById('logoutBtn');
    
    // Elementos del modal de edición
    const editServiceModal = document.getElementById('editServiceModal');
    const editServiceForm = document.getElementById('editServiceForm');
    const cancelEditBtn = document.getElementById('cancelEditBtn');
    const closeModalBtn = document.querySelector('.close');
    
    // Datos de ejemplo
    const sampleServices = [];
    const sampleAppointments = [
        {
            id: 1,
            clientName: "Laura Martínez",
            service: "Corte de cabello",
            date: "2023-06-15",
            time: "14:00",
            status: "upcoming"
        },
        {
            id: 2,
            clientName: "Carlos Rodríguez",
            service: "Manicure básico",
            date: "2023-06-16",
            time: "10:30",
            status: "upcoming"
        },
        {
            id: 3,
            clientName: "Ana Gómez",
            service: "Maquillaje social",
            date: "2023-06-10",
            time: "16:00",
            status: "completed"
        }
    ];

    const sampleReviews = [
        {
            id: 1,
            clientName: "Sofía Pérez",
            date: "2023-06-05",
            rating: 5,
            comment: "Excelente servicio, María es muy profesional y detallista. Mi cabello quedó perfecto."
        },
        {
            id: 2,
            clientName: "Juan Ramírez",
            date: "2023-05-28",
            rating: 4,
            comment: "Buen trabajo, aunque el tiempo de espera fue un poco largo. El resultado final valió la pena."
        },
        {
            id: 3,
            clientName: "Diana Castro",
            date: "2023-05-20",
            rating: 5,
            comment: "¡Increíble! El maquillaje que me hizo María fue perfecto para mi boda. Todos quedaron impresionados."
        }
    ];

    // Función para abrir el modal de edición
    window.openEditModal = function(serviceId) {
        // Aquí deberías hacer una petición al servidor para obtener los datos del servicio
        // Por ahora usaremos datos de ejemplo
        const service = {
            id: serviceId,
            name: "Servicio " + serviceId,
            category: "hair",
            price: 50000,
            description: "Descripción del servicio " + serviceId
        };
        
        // Llenar el formulario con los datos del servicio
        document.getElementById('edit-service-id').value = service.id;
        document.getElementById('edit-service-name').value = service.name;
        document.getElementById('edit-service-category').value = service.category;
        document.getElementById('edit-service-price').value = service.price;
        document.getElementById('edit-service-description').value = service.description;
        
        // Mostrar el modal
        editServiceModal.style.display = 'block';
    };

    // Cerrar el modal
    function closeEditModal() {
        editServiceModal.style.display = 'none';
    }

    // Event listeners para cerrar el modal
    closeModalBtn.addEventListener('click', closeEditModal);
    cancelEditBtn.addEventListener('click', closeEditModal);
    
    // Cerrar el modal al hacer clic fuera de él
    window.addEventListener('click', function(event) {
        if (event.target === editServiceModal) {
            closeEditModal();
        }
    });

    // Enviar formulario de edición
    editServiceForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Obtener los valores del formulario
        const serviceId = document.getElementById('edit-service-id').value;
        const name = document.getElementById('edit-service-name').value;
        const category = document.getElementById('edit-service-category').value;
        const price = parseInt(document.getElementById('edit-service-price').value);
        const description = document.getElementById('edit-service-description').value;
        
        // Aquí deberías hacer una petición AJAX para actualizar el servicio en el servidor
        console.log('Actualizando servicio:', { serviceId, name, category, price, description });
        
        // Mostrar mensaje de éxito
        showNotification('Servicio actualizado correctamente', 'success');
        
        // Cerrar el modal
        closeEditModal();
        
        // Recargar la página o actualizar la tabla (dependiendo de tu implementación)
        setTimeout(() => {
            location.reload();
        }, 1000);
    });

    // Resto del código existente (sin cambios)...
    function showNotification(message, type = 'success') {
        // Crear y mostrar una notificación (podrías implementar esto con un toast)
        alert(message); // Simplificado para este ejemplo
    }

    // Event Listeners
    userProfile.addEventListener('click', function() {
        this.classList.toggle('active');
    });

    addServiceBtn.addEventListener('click', function() {
        // Limpiar el formulario
        addServiceForm.reset();
        addServiceForm.removeAttribute('data-edit-id');
        addServiceSection.querySelector('h3').textContent = 'Agregar nuevo servicio';
        
        // Mostrar la sección
        addServiceSection.style.display = 'block';
        window.scrollTo({
            top: addServiceSection.offsetTop - 100,
            behavior: 'smooth'
        });
    });

    cancelServiceBtn.addEventListener('click', function() {
        addServiceSection.style.display = 'none';
    });

    addServiceForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Obtener los valores del formulario
        const name = document.getElementById('service-name').value;
        const category = document.getElementById('service-category').value;
        const price = parseInt(document.getElementById('service-price').value);
        const description = document.getElementById('service-description').value;
        
        // Verificar si estamos editando o agregando
        const isEditing = this.hasAttribute('data-edit-id');
        
        if (isEditing) {
            // Editar servicio existente
            const serviceId = parseInt(this.getAttribute('data-edit-id'));
            const serviceIndex = sampleServices.findIndex(s => s.id === serviceId);
            
            if (serviceIndex !== -1) {
                sampleServices[serviceIndex] = {
                    ...sampleServices[serviceIndex],
                    name,
                    category,
                    price,
                    description
                };
                
                showNotification('Servicio actualizado correctamente', 'success');
            }
        } else {
            // Agregar nuevo servicio
            const newService = {
                id: sampleServices.length > 0 ? Math.max(...sampleServices.map(s => s.id)) + 1 : 1,
                name,
                category,
                price,
                description
            };
            
            sampleServices.push(newService);
            showNotification('Servicio agregado correctamente', 'success');
        }
        
        // Actualizar la lista y ocultar el formulario
        renderServices();
        addServiceSection.style.display = 'none';
    });

    // Cerrar el menú desplegable al hacer clic fuera de él
    document.addEventListener('click', function(e) {
        if (!userProfile.contains(e.target) && !dropdownMenu.contains(e.target)) {
            userProfile.classList.remove('active');
        }
    });

    // Cambiar a vista de cliente - Redirige a inicio.html
    switchToClientBtn.addEventListener('click', function(e) {
        e.preventDefault();
        window.location.href = 'inicio.html';
    });

    // Simular cierre de sesión
    logoutBtn.addEventListener('click', function(e) {
        e.preventDefault();
        if (confirm('¿Estás seguro de que deseas cerrar sesión?')) {
            // En una aplicación real, aquí limpiarías la sesión y redirigirías al login
            window.location.href = 'login.html'; // Ajusta según tu estructura
        }
    });

    // Inicializar la aplicación
    renderServices();
    renderAppointments();
    renderReviews();
});

// Código de paginación (sin cambios)
document.addEventListener("DOMContentLoaded", function() {
    const rows = document.querySelectorAll("#servicesBody tr");
    const rowsPerPage = 5;
    let currentPage = 0;

    const totalPages = Math.ceil(rows.length / rowsPerPage);
    const pageInfo = document.getElementById("pageInfo");
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");

    function showPage(page) {
        rows.forEach((row, index) => {
            row.style.display = (index >= page * rowsPerPage && index < (page + 1) * rowsPerPage) 
                                ? "" 
                                : "none";
        });

        pageInfo.textContent = `Página ${page + 1} de ${totalPages}`;
        prevBtn.disabled = page === 0;
        nextBtn.disabled = page === totalPages - 1;
    }

    prevBtn.addEventListener("click", () => {
        if (currentPage > 0) {
            currentPage--;
            showPage(currentPage);
        }
    });

    nextBtn.addEventListener("click", () => {
        if (currentPage < totalPages - 1) {
            currentPage++;
            showPage(currentPage);
        }
    });

    showPage(currentPage); // Mostrar la primera página al cargar
});