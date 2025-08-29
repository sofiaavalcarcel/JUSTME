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

    // Datos de ejemplo
    const sampleServices = [
        {
            id: 1,
            name: "Corte de cabello",
            category: "hair",
            duration: 45,
            price: 45000,
            description: "Corte de cabello personalizado según tu estilo y preferencias."
        },
        {
            id: 2,
            name: "Manicure básico",
            category: "nails",
            duration: 60,
            price: 35000,
            description: "Manicure completo con limpieza, corte y esmaltado básico."
        },
        {
            id: 3,
            name: "Maquillaje social",
            category: "makeup",
            duration: 90,
            price: 80000,
            description: "Maquillaje profesional para eventos sociales o especiales."
        }
    ];

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

    // Funciones para renderizar datos
    function renderServices() {
        servicesList.innerHTML = '';
        sampleServices.forEach(service => {
            const serviceCard = document.createElement('div');
            serviceCard.className = 'service-card';
            serviceCard.innerHTML = `
                <h4>${service.name}</h4>
                <p><i class="fas fa-tag"></i> ${getCategoryName(service.category)}</p>
                <p><i class="fas fa-clock"></i> ${service.duration} minutos</p>
                <p class="service-price"><i class="fas fa-dollar-sign"></i> $${service.price.toLocaleString()}</p>
                <p><i class="fas fa-align-left"></i> ${service.description}</p>
                <div class="service-actions">
                    <button class="btn-icon edit" data-id="${service.id}"><i class="fas fa-edit"></i></button>
                    <button class="btn-icon delete" data-id="${service.id}"><i class="fas fa-trash"></i></button>
                </div>
            `;
            servicesList.appendChild(serviceCard);
        });

        // Agregar event listeners a los botones
        document.querySelectorAll('.service-actions .edit').forEach(btn => {
            btn.addEventListener('click', function() {
                const serviceId = parseInt(this.getAttribute('data-id'));
                editService(serviceId);
            });
        });

        document.querySelectorAll('.service-actions .delete').forEach(btn => {
            btn.addEventListener('click', function() {
                const serviceId = parseInt(this.getAttribute('data-id'));
                deleteService(serviceId);
            });
        });
    }

    function renderAppointments() {
        appointmentsList.innerHTML = '';
        sampleAppointments.forEach(appointment => {
            const appointmentCard = document.createElement('div');
            appointmentCard.className = 'appointment-card';
            appointmentCard.innerHTML = `
                <h4>${appointment.clientName}</h4>
                <p><i class="fas fa-spa"></i> ${appointment.service}</p>
                <p><i class="fas fa-calendar-day"></i> ${formatDate(appointment.date)}</p>
                <p><i class="fas fa-clock"></i> ${appointment.time}</p>
                <span class="status ${appointment.status}">
                    ${appointment.status === 'upcoming' ? 'Próxima' : 'Completada'}
                </span>
            `;
            appointmentsList.appendChild(appointmentCard);
        });
    }

    function renderReviews() {
        reviewsList.innerHTML = '';
        sampleReviews.forEach(review => {
            const reviewCard = document.createElement('div');
            reviewCard.className = 'review-card';
            reviewCard.innerHTML = `
                <div class="review-header">
                    <span class="reviewer">${review.clientName}</span>
                    <span class="review-date">${formatDate(review.date)}</span>
                </div>
                <div class="review-stars">
                    ${renderStars(review.rating)}
                </div>
                <div class="review-content">
                    ${review.comment}
                </div>
            `;
            reviewsList.appendChild(reviewCard);
        });
    }

    // Funciones auxiliares
    function getCategoryName(category) {
        const categories = {
            'hair': 'Cabello',
            'makeup': 'Maquillaje',
            'nails': 'Uñas',
            'facial': 'Facial',
            'waxing': 'Depilación',
            'other': 'Otro'
        };
        return categories[category] || category;
    }

    function formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString('es-ES', options);
    }

    function renderStars(rating) {
        let stars = '';
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars += '<i class="fas fa-star"></i>';
            } else if (i - 0.5 <= rating) {
                stars += '<i class="fas fa-star-half-alt"></i>';
            } else {
                stars += '<i class="far fa-star"></i>';
            }
        }
        return stars;
    }

    // Funciones para manejar servicios
    function editService(serviceId) {
        const service = sampleServices.find(s => s.id === serviceId);
        if (!service) return;

        // Mostrar el formulario con los datos del servicio
        document.getElementById('service-name').value = service.name;
        document.getElementById('service-category').value = service.category;
        document.getElementById('service-duration').value = service.duration;
        document.getElementById('service-price').value = service.price;
        document.getElementById('service-description').value = service.description;

        // Mostrar la sección de agregar servicio (que también usaremos para editar)
        addServiceSection.style.display = 'block';
        window.scrollTo({
            top: addServiceSection.offsetTop - 100,
            behavior: 'smooth'
        });

        // Cambiar el título del formulario
        addServiceSection.querySelector('h3').textContent = 'Editar servicio';

        // Agregar un atributo para saber que estamos editando
        addServiceForm.setAttribute('data-edit-id', serviceId);
    }

    function deleteService(serviceId) {
        if (confirm('¿Estás seguro de que deseas eliminar este servicio?')) {
            // En una aplicación real, aquí haríamos una petición al servidor
            const index = sampleServices.findIndex(s => s.id === serviceId);
            if (index !== -1) {
                sampleServices.splice(index, 1);
                renderServices();
                showNotification('Servicio eliminado correctamente', 'success');
            }
        }
    }

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
        const duration = parseInt(document.getElementById('service-duration').value);
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
                    duration,
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
                duration,
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