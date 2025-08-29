document.addEventListener("DOMContentLoaded", function () {
  // Datos de ejemplo
  const userData = {
    name: "María González",
    email: "maria@example.com",
    phone: "+57 310 123 4567",
    avatar: "https://randomuser.me/api/portraits/women/44.jpg",
    servicesCompleted: 15,
    rating: 4.8,
    favoritePros: 3,
  };

  const upcomingAppointments = [
    {
      id: 1,
      service: "Corte y Coloración",
      professional: "Ana López",
      date: "2023-12-15",
      time: "14:00",
      address: "Calle 123 #45-67, Bogotá",
      status: "upcoming",
    },
    {
      id: 2,
      service: "Manicure y Pedicure",
      professional: "Carlos Méndez",
      date: "2023-12-20",
      time: "10:30",
      address: "Carrera 89 #12-34, Bogotá",
      status: "upcoming",
    },
  ];

  const reviews = [
    {
      id: 1,
      professional: "Ana López",
      date: "2023-11-15",
      rating: 5,
      comment:
        "María es una cliente excelente, muy respetuosa y puntual. ¡La recomiendo!",
    },
    {
      id: 2,
      professional: "Carlos Méndez",
      date: "2023-10-22",
      rating: 5,
      comment:
        "Muy buen trato y siempre paga a tiempo. Es un placer trabajar con ella.",
    },
    {
      id: 3,
      professional: "Luisa Fernández",
      date: "2023-09-10",
      rating: 4,
      comment: "Buena cliente, aunque a veces cambia la cita a último momento.",
    },
  ];

  // Elementos del DOM
  const userProfile = document.getElementById("userProfile");
  const dropdownMenu = document.getElementById("dropdownMenu");
  const logoutBtn = document.getElementById("logoutBtn");
  const appointmentsList = document.querySelector(".appointments-list");
  const reviewsList = document.querySelector(".reviews-list");

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
    if (confirm("¿Estás seguro que deseas cerrar sesión?")) {
      // Redirigir a la página de login
      window.location.href = "principal.html";
    }
  });

  // Formatear fecha
  function formatDate(dateString) {
    const options = { year: "numeric", month: "long", day: "numeric" };
    return new Date(dateString).toLocaleDateString("es-ES", options);
  }

  // Generar estrellas
  function generateStars(rating) {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    let stars = "";

    for (let i = 1; i <= 5; i++) {
      if (i <= fullStars) {
        stars += '<i class="fas fa-star"></i>';
      } else if (i === fullStars + 1 && hasHalfStar) {
        stars += '<i class="fas fa-star-half-alt"></i>';
      } else {
        stars += '<i class="far fa-star"></i>';
      }
    }

    return stars;
  }

  // Renderizar citas próximas
  function renderAppointments() {
    if (upcomingAppointments.length === 0) {
      appointmentsList.innerHTML =
        '<p class="no-appointments">No tienes citas próximas programadas.</p>';
      return;
    }

    let html = "";
    upcomingAppointments.forEach((appointment) => {
      html += `
                <div class="appointment-card">
                    <h4>${appointment.service}</h4>
                    <p><i class="fas fa-user-tie"></i> ${
                      appointment.professional
                    }</p>
                    <p><i class="fas fa-calendar-day"></i> ${formatDate(
                      appointment.date
                    )} a las ${appointment.time}</p>
                    <p><i class="fas fa-map-marker-alt"></i> ${
                      appointment.address
                    }</p>
                    <span class="status ${appointment.status}">Próxima</span>
                </div>
            `;
    });

    appointmentsList.innerHTML = html;
  }

  // Renderizar comentarios
  function renderReviews() {
    if (reviews.length === 0) {
      reviewsList.innerHTML =
        '<p class="no-reviews">No hay comentarios sobre ti aún.</p>';
      return;
    }

    let html = "";
    reviews.forEach((review) => {
      html += `
                <div class="review-card">
                    <div class="review-header">
                        <span class="reviewer">${review.professional}</span>
                        <span class="review-date">${formatDate(
                          review.date
                        )}</span>
                    </div>
                    <div class="review-stars">
                        ${generateStars(review.rating)}
                    </div>
                    <div class="review-content">
                        ${review.comment}
                    </div>
                </div>
            `;
    });

    reviewsList.innerHTML = html;
  }

  // Inicializar
  renderAppointments();
  renderReviews();
});
const switchToProBtn = document.getElementById("switchToProBtn");
switchToProBtn.addEventListener("click", function(e) {
  e.preventDefault();
  Swal.fire({
    title: '¿Cambiar a panel profesional?',
    text: 'Serás redirigido al panel de profesionales',
    icon: 'question',
    showCancelButton: true,
    confirmButtonColor: '#e16bff',
    cancelButtonColor: '#6c757d',
    confirmButtonText: 'Sí, cambiar',
    cancelButtonText: 'Cancelar'
  }).then((result) => {
    if (result.isConfirmed) {
      window.location.href = "profesional.html";
    }
  });
});






/*SERVICIOSSSSSSSSSSSSSSS*/

const profesionales = [
  {
    nombre: "Ana Torres",
    servicio: "makeup",
    precio: "$70.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1191973410/photo/portrait-of-young-latin-woman-in-a-studio.jpg?s=612x612&w=0&k=20&c=cys0BnOMKZOcyU-hxWd0St3SXJocqf7leezB39NXN9s=",
  },
  {
    nombre: "Sara Mendoza",
    servicio: "makeup",
    precio: "$65.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/2150172700/photo/confident-young-beautiful-woman-portrait.jpg?s=612x612&w=0&k=20&c=bbUxnZOujkKl3kDyLYU_LeDSARq7627Dwiu5XW0tMKY=",
  },
  {
    nombre: "Isabela Ruiz",
    servicio: "makeup",
    precio: "$80.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1474984560/photo/portrait-of-beautiful-smiling-woman-on-white-background.jpg?s=612x612&w=0&k=20&c=w8f-iclLXB5mh5AHkHcpurnDjEwwPPJr4iEejMrgdVA=",
  },
  {
    nombre: "Carla Pineda",
    servicio: "makeup",
    precio: "$75.000",
    estrellas: 3,
    imagen: "https://media.istockphoto.com/id/1474984560/photo/portrait-of-beautiful-smiling-woman-on-white-background.jpg?s=612x612&w=0&k=20&c=w8f-iclLXB5mh5AHkHcpurnDjEwwPPJr4iEejMrgdVA=",
  },

  {
    nombre: "Laura Gómez",
    servicio: "nails",
    precio: "$45.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1474984560/photo/portrait-of-beautiful-smiling-woman-on-white-background.jpg?s=612x612&w=0&k=20&c=w8f-iclLXB5mh5AHkHcpurnDjEwwPPJr4iEejMrgdVA=",
  },
  {
    nombre: "Valentina Ortiz",
    servicio: "nails",
    precio: "$50.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1054963296/photo/joyful-caucasian-girl-smiling-at-camera.jpg?s=612x612&w=0&k=20&c=djeVWe2IV41EN2bej4JiPbARfsVaztJ0u8uNDXDM2-M=",
  },
  {
    nombre: "Diana López",
    servicio: "nails",
    precio: "$48.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1007958330/photo/close-up-of-beautiful-woman.jpg?s=612x612&w=0&k=20&c=ndHCwUKPTVesQpKCqe0i9EO1gt27e8QSSqsKwprzenQ=",
  },
  {
    nombre: "Lucía Vega",
    servicio: "nails",
    precio: "$52.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1162058560/photo/young-beautiful-woman-looking-at-camera-on-gray-background.jpg?s=612x612&w=0&k=20&c=RidoShXprgWFouFrStGqLec7tPF_b3xcnrAA7vZU0is=",
  },

  {
    nombre: "Camila Ríos",
    servicio: "hair",
    precio: "$60.000",
    estrellas: 3,
    imagen: "https://media.istockphoto.com/id/1325913313/photo/indian-woman-crying-at-camera-white-background.jpg?s=612x612&w=0&k=20&c=iAaWwVOhFFbajAInO0KtxrrsnSQffGnl-AjwURnJQAI=",
  },
  {
    nombre: "Sofía Andrade",
    servicio: "hair",
    precio: "$62.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1009137622/photo/positive-human-emotions-headshot-of-happy-emotional-girl.jpg?s=612x612&w=0&k=20&c=EGBS0cL8Kyjy7Ze5_E66s678gLVD-1L_FNmfofwXDtc=",
  },
  {
    nombre: "Natalia Romero",
    servicio: "hair",
    precio: "$68.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1126022185/photo/happy-beautiful-young-woman-posing-at-camera.jpg?s=612x612&w=0&k=20&c=xbxLvKTcCQj5Glp9E0WWPX7tOrfrLsMyvjDa6-Vsu-Q=",
  },
  {
    nombre: "Lorena Díaz",
    servicio: "hair",
    precio: "$55.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1167544567/photo/portrait-of-young-beautiful-happy-woman-with-no-makeup-on-white-background.jpg?s=612x612&w=0&k=20&c=pj0G-XdVs2E4SWOcwG0MWzaQwWu05uR_cgGazjh2MIg=",
  },

  {
    nombre: "Paola Díaz",
    servicio: "facial",
    precio: "$80.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1062282972/photo/beautiful-young-woman-in-casuals.jpg?s=612x612&w=0&k=20&c=biC65gMWM61QB-AiK_Px9b3eM-uQB-4BkEZVg_OjKmk=",
  },
  {
    nombre: "Mariana Silva",
    servicio: "facial",
    precio: "$75.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1138619160/photo/my-biggest-and-best-smile.jpg?s=612x612&w=0&k=20&c=PJtcT3DlkunYTA2eBwhLQ-8EddUR0F9qLDbIPmzdzI0=",
  },
  {
    nombre: "Vanessa Ruiz",
    servicio: "facial",
    precio: "$78.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/1431910999/photo/close-up-portrait-of-woman-with-toothy-smile-isolated-on-gray-studio-background.jpg?s=612x612&w=0&k=20&c=xYJCSe0iowFLuRg-LWx0LDXLpAGvfMJvFl4-G1FrfC4=",
  },
  {
    nombre: "Andrea Torres",
    servicio: "facial",
    precio: "$72.000",
    estrellas: 3,
    imagen: "https://media.istockphoto.com/id/1125164745/photo/portrait-of-young-beautiful-cute-cheerful-girl-smiling-looking-at-camera-over-white-background.jpg?s=612x612&w=0&k=20&c=4ODjzXeTEZom8RbZjUEeEHoYdO7n1kaLLgJS4VNag-E=",
  },

  {
    nombre: "Juliana Cruz",
    servicio: "waxing",
    precio: "$40.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/1135939595/photo/close-up-portrait-of-young-beautiful-caucasian-woman-with-happy-face-and-beautiful-smile.jpg?s=612x612&w=0&k=20&c=dUR-sX8C3HBM-lYhYkGFM15NZstrveunr5j8wuUOHyY=",
  },
  {
    nombre: "Carolina Mora",
    servicio: "waxing",
    precio: "$42.000",
    estrellas: 5,
    imagen: "https://media.istockphoto.com/id/973726998/photo/smiling-woman-over-gray-background.jpg?s=612x612&w=0&k=20&c=M3c40ZK-6zDEf44C92sQAtyINLoe1hCbwKHc5B2d4t4=",
  },
  {
    nombre: "Estefanía Rojas",
    servicio: "waxing",
    precio: "$38.000",
    estrellas: 3,
    imagen: "https://media.istockphoto.com/id/1302308607/photo/portrait-of-smiling-young-woman.jpg?s=612x612&w=0&k=20&c=FYDwqsvON26lmpGoHLbv3x0S5UXONfPiEaW7qo1lvC8=",
  },
  {
    nombre: "Daniela Pérez",
    servicio: "waxing",
    precio: "$45.000",
    estrellas: 4,
    imagen: "https://media.istockphoto.com/id/2176766274/photo/close-up-photo-of-a-gorgeous-positive-arabian-indian-or-caucasian-young-brown-eyed-brunette.jpg?s=612x612&w=0&k=20&c=IXQ7kN5K7RclnHeqmuSqroXNFXWpWW7iJtobr9asxto=",
  },
];

const serviceNames = {
  hair: "Corte de Cabello",
  makeup: "Maquillaje",
  nails: "Uñas",
  facial: "Facial",
  waxing: "Depilación",
};

const modal = document.getElementById("myModal");
const btn = document.getElementById("openModalBtn");
const span = document.querySelector("#myModal .close");
const container = document.getElementById("profesionalesContainer");

btn.onclick = function () {
  // Mostrar elementos ocultos
  span.classList.remove("oculto");
  document.querySelector("#myModal h2").classList.remove("oculto");

  const selectedService = document.getElementById("service-type").value;
  container.innerHTML = "";

  const serviciosUnicos =
    selectedService !== ""
      ? [selectedService]
      : [...new Set(profesionales.map((p) => p.servicio))];

  serviciosUnicos.forEach((servicio) => {
    const grupo = profesionales
      .filter((p) => p.servicio === servicio)
      .slice(0, 4);

    if (grupo.length > 0) {
      const titulo = document.createElement("h3");
      container.appendChild(titulo);

      const grid = document.createElement("div");
      if (selectedService === "") {
        grid.classList.add("profesionales-grid-multigrupo");
      } else {
        grid.classList.add("profesionales-grid");
      }

      grupo.forEach((p) => {
        const estrellas = "⭐".repeat(p.estrellas) + "☆".repeat(5 - p.estrellas);
        grid.innerHTML += `
          <div class="pro-card">
            <img src="${p.imagen}" alt="${p.nombre}">
            <h3>${p.nombre}</h3>
            <p>Servicio: ${serviceNames[p.servicio] || p.servicio}</p>
            <p>Precio: ${p.precio}</p>
            <div class="stars">${estrellas}</div>
            <button class="reservar-btn">Reservar Servicio</button>

          </div>
        `;
      });

      container.appendChild(grid);
    }
  });

  // Mostrar modal: quitar .oculto y luego agregar .mostrar
  modal.classList.remove("oculto");
  requestAnimationFrame(() => {
    modal.classList.add("mostrar");
  });
}
// Cerrar modal al hacer clic en la X
span.onclick = function () {
  modal.classList.remove("mostrar");
  modal.classList.add("oculto");
};

// Cerrar modal al hacer clic fuera del contenido
window.onclick = function (event) {
  if (event.target === modal) {
    modal.classList.remove("mostrar");
    setTimeout(() => {
      modal.classList.add("oculto");
    }, 300);
  }
};











// Variables de estado
let currentPaymentStep = 1;
const totalPaymentSteps = 3;
let selectedPaymentMethod = 'PSE - Bancolombia';

// Función para inicializar el modal de pago
function initPaymentModal() {
    // Delegación de eventos para los botones de reserva
    document.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('reservar-btn')) {
            e.preventDefault();
            openPaymentModal(e.target);
        }
    });

    // Configurar eventos del modal de pago
    setupPaymentModalEvents();
}

// Función para configurar los eventos del modal de pago
function setupPaymentModalEvents() {
    // Cerrar modal
    document.getElementById('paymentModalCloseBtn').addEventListener('click', closePaymentModal);
    
    // Navegación entre pasos
    document.getElementById('paymentNextToStep2Btn').addEventListener('click', function(e) {
        e.preventDefault();
        nextStep();
    });
    
    document.getElementById('paymentNextToStep3Btn').addEventListener('click', function(e) {
        e.preventDefault();
        if (validatePaymentForm()) {
            nextStep();
            // Actualizar el método de pago en la confirmación
            document.getElementById('confirmationPaymentMethod').textContent = selectedPaymentMethod;
        } else {
            Swal.fire('Error', 'Por favor completa todos los campos requeridos', 'warning');
        }
    });
    
    document.getElementById('paymentBackToStep1Btn').addEventListener('click', function(e) {
        e.preventDefault();
        prevStep();
    });
    
    document.getElementById('paymentBackToStep2Btn').addEventListener('click', function(e) {
        e.preventDefault();
        prevStep();
    });
    
    document.getElementById('paymentFinishProcessBtn').addEventListener('click', function(e) {
        e.preventDefault();
        Swal.fire('¡Éxito!', 'Reserva completada correctamente', 'success')
            .then(() => {
                closePaymentModal();
            });
    });
    
    // Cambio entre métodos de pago
    document.querySelectorAll('.payment-method-tab').forEach(tab => {
        tab.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remover clase active de todos los tabs
            document.querySelectorAll('.payment-method-tab').forEach(t => {
                t.classList.remove('active');
            });
            
            // Añadir clase active al tab clickeado
            this.classList.add('active');
            
            // Ocultar todos los contenidos de métodos
            document.querySelectorAll('.payment-method-content').forEach(content => {
                content.classList.remove('active');
            });
            
            // Mostrar solo el contenido correspondiente
            const target = this.getAttribute('data-target');
            document.getElementById(target).classList.add('active');
            
            // Actualizar el método de pago seleccionado
            if (target === 'pseMethod') {
                const bank = document.getElementById('pseBankSelect').value || 'Bancolombia';
                selectedPaymentMethod = `PSE - ${bank}`;
            } else if (target === 'cardMethod') {
                selectedPaymentMethod = 'Tarjeta de crédito/débito';
            } else {
                selectedPaymentMethod = 'Efectivo';
            }
        });
    });
    
    // Mostrar/ocultar información de transferencia
    document.getElementById('cashPaymentMethodSelect').addEventListener('change', function() {
        const transferInfo = document.getElementById('transferInfoSection');
        if (this.value === 'transferencia') {
            transferInfo.style.display = 'block';
            selectedPaymentMethod = 'Transferencia bancaria';
        } else {
            transferInfo.style.display = 'none';
            selectedPaymentMethod = 'Efectivo';
        }
    });
}

// Función para abrir el modal de pago
function openPaymentModal(button) {
    const card = button.closest('.pro-card');
    if (card) {
        const profesional = card.querySelector('h3').textContent;
        const servicio = card.querySelector('p:nth-of-type(1)').textContent.replace('Servicio: ', '');
        const precio = card.querySelector('p:nth-of-type(2)').textContent.replace('Precio: ', '');
        
        // Actualizar datos en el modal
        document.getElementById('reservationServiceName').textContent = servicio;
        document.getElementById('reservationProfessionalName').textContent = profesional;
        document.getElementById('paymentServiceName').textContent = servicio;
        document.getElementById('paymentProfessionalName').textContent = profesional;
        document.getElementById('paymentTotalAmount').textContent = precio;
        document.getElementById('confirmationServiceName').textContent = servicio;
        document.getElementById('confirmationProfessionalName').textContent = profesional;
        
        // Generar fecha de ejemplo
        const today = new Date();
        const tomorrow = new Date(today);
        tomorrow.setDate(today.getDate() + 1);
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        const fechaReserva = tomorrow.toLocaleDateString('es-ES', options) + ' - 2:00 PM';
        
        document.getElementById('reservationDateTime').textContent = fechaReserva;
        document.getElementById('paymentDateTime').textContent = fechaReserva;
        document.getElementById('confirmationDateTime').textContent = fechaReserva;
    }
    
    // Mostrar modal
    document.getElementById('paymentModalOverlay').classList.add('active');
    resetPaymentProcess();
}

// Funciones auxiliares
function nextStep() {
    if (currentPaymentStep < totalPaymentSteps) {
        currentPaymentStep++;
        updatePaymentProgressBar();
        showPaymentStep(currentPaymentStep);
    }
}

function prevStep() {
    if (currentPaymentStep > 1) {
        currentPaymentStep--;
        updatePaymentProgressBar();
        showPaymentStep(currentPaymentStep);
    }
}

function updatePaymentProgressBar() {
    const progressPercentage = ((currentPaymentStep - 1) / (totalPaymentSteps - 1)) * 100;
    document.getElementById('paymentProgressLine').style.width = `${progressPercentage}%`;
    
    document.querySelectorAll('.payment-step').forEach((step, index) => {
        step.classList.remove('active', 'completed');
        if (index + 1 < currentPaymentStep) step.classList.add('completed');
        if (index + 1 === currentPaymentStep) step.classList.add('active');
    });
}

function showPaymentStep(stepNumber) {
    document.querySelectorAll('.payment-step-content').forEach(content => {
        content.classList.remove('active');
    });
    document.getElementById(`paymentStep${stepNumber}Content`).classList.add('active');
}

function validatePaymentForm() {
    const activeMethod = document.querySelector('.payment-method-tab.active').getAttribute('data-target');
    
    if (activeMethod === 'pseMethod') {
        const bank = document.getElementById('pseBankSelect').value;
        const accountType = document.getElementById('pseAccountTypeSelect').value;
        const email = document.getElementById('pseEmailInput').value;
        
        if (!bank || !accountType || !email) return false;
        
    } else if (activeMethod === 'cardMethod') {
        const cardNumber = document.getElementById('cardNumberInput').value;
        const cardExpiry = document.getElementById('cardExpiryInput').value;
        const cardCvv = document.getElementById('cardCvvInput').value;
        const cardName = document.getElementById('cardNameInput').value;
        
        if (!cardNumber || !cardExpiry || !cardCvv || !cardName) return false;
    }
    
    return true;
}

function closePaymentModal() {
    document.getElementById('paymentModalOverlay').classList.remove('active');
}

function resetPaymentProcess() {
    currentPaymentStep = 1;
    updatePaymentProgressBar();
    showPaymentStep(currentPaymentStep);
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    initPaymentModal();
    
    // También puedes inicializar otros componentes aquí
    // initUserProfile();
    // initServiceSearch();
});