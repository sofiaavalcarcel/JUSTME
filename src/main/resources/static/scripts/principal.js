document.addEventListener('DOMContentLoaded', () => {
    // ================================
    // Datos de servicios
    // ================================
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

    // ================================
    // Efectos de scroll en navbar
    // ================================
    window.addEventListener('scroll', () => {
        const nav = document.querySelector('nav');
        if (window.scrollY > 50) {
            nav.style.backgroundColor = 'rgba(255, 255, 255, 0.95)';
            nav.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
        } else {
            nav.style.backgroundColor = '#fff';
            nav.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
        }
    });

    // ================================
    // Preloader
    // ================================
    const preloader = document.getElementById('preloader');
    const loadingProgress = document.querySelector('.loading-progress');
    let progress = 0;
    const progressInterval = setInterval(() => {
        progress += Math.random() * 15;
        if (progress >= 100) {
            progress = 100;
            clearInterval(progressInterval);
        }
        loadingProgress.style.width = progress + '%';
    }, 300);

    window.addEventListener('load', () => {
        setTimeout(() => {
            loadingProgress.style.width = '100%';
            setTimeout(() => {
                preloader.classList.add('hidden');
                setTimeout(() => {
                    preloader.style.display = 'none';
                }, 500);
            }, 300);
        }, 1500);
    });

    window.addEventListener('error', () => {
        clearInterval(progressInterval);
        loadingProgress.style.width = '100%';
        setTimeout(() => {
            preloader.classList.add('hidden');
            setTimeout(() => {
                preloader.style.display = 'none';
            }, 500);
        }, 500);
    });

    // ================================
    // Modales Login y Registro
    // ================================
    const modalOverlay = document.getElementById('modalOverlay');
    const loginModal = document.getElementById('loginModal');
    const registerModal = document.getElementById('registerModal');
    const loginBtn = document.querySelector('.login');
    const signupBtn = document.querySelector('.signup');
    const goToRegister = document.getElementById('goToRegister');
    const goToLogin = document.getElementById('goToLogin');
    const closeModalBtns = document.querySelectorAll('.close-modal');

    function openModal(modal) {
        modalOverlay.classList.add('active');
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
    function closeModal(modal) {
        modalOverlay.classList.remove('active');
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }

    if (loginBtn) loginBtn.addEventListener('click', () => openModal(loginModal));
    if (signupBtn) signupBtn.addEventListener('click', () => openModal(registerModal));

    if (goToRegister) goToRegister.addEventListener('click', e => {
        e.preventDefault();
        closeModal(loginModal);
        openModal(registerModal);
    });

    if (goToLogin) goToLogin.addEventListener('click', e => {
        e.preventDefault();
        closeModal(registerModal);
        openModal(loginModal);
    });

    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const modal = btn.closest('.modal');
            closeModal(modal);
        });
    });

    if (modalOverlay) {
        modalOverlay.addEventListener('click', () => {
            closeModal(loginModal);
            closeModal(registerModal);
        });
    }

    document.addEventListener('keydown', e => {
        if (e.key === 'Escape') {
            closeModal(loginModal);
            closeModal(registerModal);
        }
    });

    // ================================
    // Modal de Contacto
    // ================================
    const contactLink = document.getElementById('contactLink');
    const contactModal = document.getElementById('contactModal');
    const closeContact = document.getElementById('closeModal');
    const contactForm = document.getElementById('contactForm');

    if (contactLink) {
        contactLink.addEventListener('click', e => {
            e.preventDefault();
            contactModal.style.display = 'block';
        });
    }

    if (closeContact) {
        closeContact.addEventListener('click', () => {
            contactModal.style.display = 'none';
        });
    }

    window.addEventListener('click', event => {
        if (event.target === contactModal) {
            contactModal.style.display = 'none';
        }
    });

    if (contactForm) {
        contactForm.addEventListener('submit', e => {
            e.preventDefault();
            alert('Formulario enviado con éxito!');
            contactModal.style.display = 'none';
            contactForm.reset();
        });
    }
});
