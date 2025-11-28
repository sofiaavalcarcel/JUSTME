
document.addEventListener('DOMContentLoaded', () => {
	// SweetAlert para contraseña cambiada correctamente
	try {
		const params = new URLSearchParams(window.location.search);
		if (typeof Swal !== 'undefined' && params.get('resetSuccess') === 'true') {
			Swal.fire({
				icon: 'success',
				title: 'Contraseña actualizada',
				text: 'Tu contraseña se cambió correctamente. Ahora puedes iniciar sesión con tu nueva contraseña.',
				confirmButtonText: 'Aceptar'
			});
		}
	} catch (e) {
		console.error('Error evaluando parámetros de URL para SweetAlert', e);
	}
	// ================================
	// Datos de servicios
	// ================================
	const services = [
		{
			title: "Corte de Cabello",
			description: "Corte profesional a domicilio para hombres y mujeres, con estilo personalizado.",
			image: "https://images.unsplash.com/photo-1605497788044-5a32c7078486?auto=format&fit=crop&w=1350&q=80"
		},
		{
			title: "Maquillaje Profesional",
			description: "Maquillaje para ocasiones especiales, eventos o diario, realzando tu belleza natural.",
			image: "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?auto=format&fit=crop&w=1350&q=80"
		},
		{
			title: "Manicure & Pedicure",
			description: "Servicio completo de uñas con esmaltado semipermanente y cuidado de manos/pies.",
			image: "https://images.pexels.com/photos/8187421/pexels-photo-8187421.jpeg"
		},
		{
			title: "Tratamiento Facial",
			description: "Limpieza facial profunda con hidratación y masaje relajante para una piel radiante.",
			image: "https://images.pexels.com/photos/3852204/pexels-photo-3852204.jpeg"
		},
		{
			title: "Depilación",
			description: "Servicio de depilación con cera en diferentes zonas del cuerpo.",
			image: "https://images.pexels.com/photos/19239109/pexels-photo-19239109.jpeg"
		},
		{
			title: "Coloración Capilar",
			description: "Tinte profesional y tratamientos de color para un cabello vibrante y saludable.",
			image: "https://images.pexels.com/photos/8468157/pexels-photo-8468157.jpeg"
		},
		{
			title: "Masajes Corporales",
			description: "Relajantes, descontracturantes o reductores con aceites esenciales para aliviar tensiones.",
			image: "https://images.pexels.com/photos/6560289/pexels-photo-6560289.jpeg"
		},
		{
			title: "Diseño de Cejas y Pestañas",
			description: "Depilación, perfilado, tinte o laminado de cejas. Extensiones o lifting de pestañas.",
			image: "https://images.pexels.com/photos/8826403/pexels-photo-8826403.jpeg"
		}
	];

	const servicesGrid = document.querySelector('.services-grid');
	if (servicesGrid) {
		services.forEach(service => {
			const serviceCard = document.createElement('div');
			serviceCard.className = 'service-card';
			serviceCard.innerHTML = `
				<div class="service-img" style="background-image: url('${service.image}')"></div>
				<div class="service-info">
					<h3>${service.title}</h3>
					<p>${service.description}</p>
				</div>
			`;
			servicesGrid.appendChild(serviceCard);
		});
	}

	// ================================
	// Navbar scroll
	// ================================
	window.addEventListener('scroll', () => {
		const nav = document.querySelector('nav');
		if (!nav) return;
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
		if (loadingProgress) {
			loadingProgress.style.width = progress + '%';
		}
	}, 300);

	function hidePreloader() {
		if (!preloader) return;
		if (loadingProgress) loadingProgress.style.width = '100%';
		setTimeout(() => {
			preloader.classList.add('hidden');
			setTimeout(() => {
				preloader.style.display = 'none';
			}, 500);
		}, 300);
	}

	window.addEventListener('load', () => setTimeout(hidePreloader, 1500));
	window.addEventListener('error', () => hidePreloader());
	setTimeout(hidePreloader, 5000);

	// ================================
	// Modales (Login, Contacto, Términos)
	// ================================
	const modalOverlay = document.getElementById('modalOverlay');
	const loginModal = document.getElementById('loginModal');
	const loginBtn = document.querySelector('.login');
	const termsModal = document.getElementById('termsModal');
	const contactModal = document.getElementById('contactModal');

	// Abrir modal
	function openModal(modal) {
		if (!modal) return;
		modal.classList.add('active');
		if (modalOverlay) modalOverlay.classList.add('active');
	}

	// Cerrar modal específico
	function closeSingleModal(modal) {
		if (!modal) return;
		modal.classList.remove('active');

		// Si no quedan modales activos, ocultar overlay
		const anyOpen = document.querySelector('.modal.active');
		if (!anyOpen && modalOverlay) {
			modalOverlay.classList.remove('active');
		}
	}

	// Login
	if (loginBtn && loginModal) {
		loginBtn.addEventListener('click', () => openModal(loginModal));
	}

	// Términos
	const openTermsLinks = document.querySelectorAll('.open-terms');
	if (openTermsLinks && termsModal) {
		openTermsLinks.forEach(link => {
			link.addEventListener('click', e => {
				e.preventDefault();
				openModal(termsModal);
			});
		});
	}

	// Contacto
	const contactLink = document.getElementById('contactLink');
	if (contactLink && contactModal) {
		contactLink.addEventListener('click', (e) => {
			e.preventDefault();
			openModal(contactModal);
		});
	}

	// Botones de cerrar (x)
	document.querySelectorAll('.close-modal').forEach(btn => {
		btn.addEventListener('click', function() {
			const modal = this.closest('.modal');
			closeSingleModal(modal);
		});
	});

	// Cerrar al hacer click en overlay
	if (modalOverlay) {
		modalOverlay.addEventListener('click', () => {
			const openModalElement = document.querySelector('.modal.active');
			if (openModalElement) {
				closeSingleModal(openModalElement);
			}
		});
	}

	// ================================
	// Validación Términos y Condiciones
	// ================================
	const loginForm = document.getElementById('loginForm');
	if (loginForm) {
		loginForm.addEventListener('submit', function(e) {
			const termsCheckbox = document.getElementById('terms');
			const termsError = document.getElementById('termsError');
			if (termsCheckbox && !termsCheckbox.checked) {
				e.preventDefault();
				if (termsError) termsError.style.display = "block";
			} else if (termsError) {
				termsError.style.display = "none";
			}
		});
	}

	// Toggle mostrar/ocultar contraseña en login
	document.querySelectorAll('.toggle-password').forEach(btn => {
		btn.addEventListener('click', () => {
			const targetId = btn.getAttribute('data-target');
			const input = document.getElementById(targetId);
			const icon = btn.querySelector('i');
			if (!input) return;
			if (input.type === 'password') {
				input.type = 'text';
				if (icon) {
					icon.classList.remove('fa-eye');
					icon.classList.add('fa-eye-slash');
				}
			} else {
				input.type = 'password';
				if (icon) {
					icon.classList.remove('fa-eye-slash');
					icon.classList.add('fa-eye');
				}
			}
		});
	});
});

