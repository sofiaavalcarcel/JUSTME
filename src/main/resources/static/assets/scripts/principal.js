document.addEventListener('DOMContentLoaded', function() {
    // Datos de servicios
    const services = [
        {
            title: "Corte de Cabello",
            description: "Corte profesional a domicilio para hombres y mujeres, con estilo personalizado.",
            price: "$60,000",
            image: "https://images.unsplash.com/photo-1605497788044-5a32c7078486?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
        },
        {
            title: "Maquillaje Profesional",
            description: "Maquillaje para ocasiones especiales, eventos o diario, realzando tu belleza natural.",
            price: "$120,000",
            image: "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
        },
        {
            title: "Manicure & Pedicure",
            description: "Servicio completo de uñas con esmaltado semipermanente y cuidado de manos/pies.",
            price: "$80,000",
            image: "https://images.pexels.com/photos/8187421/pexels-photo-8187421.jpeg"
        },
        {
            title: "Tratamiento Facial",
            description: "Limpieza facial profunda con hidratación y masaje relajante para una piel radiante.",
            price: "$150,000",
            image: "https://images.pexels.com/photos/3852204/pexels-photo-3852204.jpeg"
        },
        {
            title: "Depilación",
            description: "Servicio de depilación con cera en diferentes zonas del cuerpo.",
            price: "$70,000 - $200,000",
            image: "https://images.pexels.com/photos/19239109/pexels-photo-19239109.jpeg"
        },
        {
            title: "Coloración Capilar",
            description: "Tinte profesional y tratamientos de color para un cabello vibrante y saludable.",
            price: "$180,000",
            image: "https://images.pexels.com/photos/8468157/pexels-photo-8468157.jpeg"
        },
                {
            title: "Masajes Corporales",
            description: "Relajantes, descontracturantes o reductores. Se utilizan aceites esenciales y técnicas manuales para aliviar tensiones y mejorar la circulación.",
            price: "$180,000",
            image: "https://images.pexels.com/photos/6560289/pexels-photo-6560289.jpeg"
        },
                {
            title: "Diseño de Cejas y Pestañas",
            description: "Depilación, perfilado, tinte o laminado de cejas. Extensiones, lifting o tintura de pestañas para realzar la mirada.",
            price: "$180,000",
            image: "https://images.pexels.com/photos/8826403/pexels-photo-8826403.jpeg"
        }
    ];

    // Llenar la sección de servicios
    const servicesGrid = document.querySelector('.services-grid');
    
    services.forEach(service => {
        const serviceCard = document.createElement('div');
        serviceCard.className = 'service-card';
        
        serviceCard.innerHTML = `
            <div class="service-img" style="background-image: url('${service.image}')"></div>
            <div class="service-info">
                <h3>${service.title}</h3>
                <p>${service.description}</p>
                <p class="service-price">${service.price}</p>
            </div>
        `;
        
        servicesGrid.appendChild(serviceCard);
    });

    // Efectos de scroll
    window.addEventListener('scroll', function() {
        const nav = document.querySelector('nav');
        if (window.scrollY > 50) {
            nav.style.backgroundColor = 'rgba(255, 255, 255, 0.95)';
            nav.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
        } else {
            nav.style.backgroundColor = '#fff';
            nav.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
        }
    });

    // Botones de autenticación
    const loginBtn = document.querySelector('.login');
    const signupBtn = document.querySelector('.signup');
    


    // Botones CTA
    const primaryCta = document.querySelector('.primary-cta');
    const secondaryCta = document.querySelector('.secondary-cta');
    
    primaryCta.addEventListener('click', function() {
        window.location.href = '#services';
    });
    
    secondaryCta.addEventListener('click', function() {
        window.location.href = '#services';
    });
});

    const modalOverlay = document.getElementById('modalOverlay');
    const loginModal = document.getElementById('loginModal');
    const registerModal = document.getElementById('registerModal');
    const loginBtn = document.querySelector('.login');
    const signupBtn = document.querySelector('.signup');
    const goToRegister = document.getElementById('goToRegister');
    const goToLogin = document.getElementById('goToLogin');
    const closeModalBtns = document.querySelectorAll('.close-modal');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    // Abrir modal de login
    loginBtn.addEventListener('click', () => {
        openModal(loginModal);
    });

    // Abrir modal de registro
    signupBtn.addEventListener('click', () => {
        openModal(registerModal);
    });

    // Cambiar a registro desde login
    goToRegister.addEventListener('click', (e) => {
        e.preventDefault();
        closeModal(loginModal);
        openModal(registerModal);
    });

    // Cambiar a login desde registro
    goToLogin.addEventListener('click', (e) => {
        e.preventDefault();
        closeModal(registerModal);
        openModal(loginModal);
    });

    // Cerrar modales
    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const modal = btn.closest('.modal');
            closeModal(modal);
        });
    });

    // Cerrar al hacer clic fuera del modal
    modalOverlay.addEventListener('click', () => {
        closeModal(loginModal);
        closeModal(registerModal);
    });

    // Función para abrir modal
    function openModal(modal) {
        modalOverlay.classList.add('active');
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    // Función para cerrar modal
    function closeModal(modal) {
        modalOverlay.classList.remove('active');
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }

    // Manejar envío del formulario de login
    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;
        
        // Aquí iría la validación real con el backend
        console.log('Login attempt with:', email, password);
        
        // Simular inicio de sesión exitoso
        setTimeout(() => {
            closeModal(loginModal);
            window.location.href = 'inicio.html';
        }, 1000);
    });

    // Manejar envío del formulario de registro
    registerForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const name = document.getElementById('registerName').value;
        const email = document.getElementById('registerEmail').value;
        const phone = document.getElementById('registerPhone').value;
        const password = document.getElementById('registerPassword').value;
        const confirmPassword = document.getElementById('registerConfirm').value;
        
        // Validar que las contraseñas coincidan
        if (password !== confirmPassword) {
            alert('Las contraseñas no coinciden');
            return;
        }
        
        // Aquí iría el registro real con el backend
        console.log('Register attempt with:', {name, email, phone, password});
        
        // Simular registro exitoso
        setTimeout(() => {
            closeModal(registerModal);
            window.location.href = 'inicio.html';
        }, 1000);
    });

    // Cerrar con la tecla ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            closeModal(loginModal);
            closeModal(registerModal);
        }
    });

     // modal contactanos
    document.getElementById('contactLink').addEventListener('click', function(e) {
        e.preventDefault();
        document.getElementById('contactModal').style.display = 'block';
        document.getElementById('modalNav').style.display = 'block';
    });
    
    // Cerrar modal
    document.getElementById('closeModal').addEventListener('click', function() {
        document.getElementById('contactModal').style.display = 'none';
        document.getElementById('modalNav').style.display = 'none';
    });
    
    // Cerrar al hacer clic fuera
    window.addEventListener('click', function(event) {
        if (event.target == document.getElementById('contactModal')) {
            document.getElementById('contactModal').style.display = 'none';
            document.getElementById('modalNav').style.display = 'none';
        }
    });
    
    // Enviar formulario
    document.getElementById('contactForm').addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Formulario enviado con éxito!');
        document.getElementById('contactModal').style.display = 'none';
        document.getElementById('modalNav').style.display = 'none';
        this.reset();
    });
    