// Datos simulados
const clients = [
  { name: "Danny Liu", deals: 1023, total: 37431 },
  { name: "Bella Deviant", deals: 963, total: 30423 },
  { name: "Darrell Steward", deals: 843, total: 28549 },
  { name: "Annie Cooper", deals: 772, total: 24319 },
  { name: "Cody Fisher", deals: 718, total: 22190 },
];

// Render tabla de clientes
function renderClientsTable() {
  const tbody = document.getElementById("clientsBody");
  tbody.innerHTML = ''; // Limpiar tabla antes de renderizar
  
  clients.forEach(c => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td><strong>${c.name}</strong></td>
      <td>${c.deals.toLocaleString("es-CO")}</td>
      <td>$${c.total.toLocaleString("es-CO")}</td>
    `;
    tbody.appendChild(tr);
  });
}

// Donut chart (servicios)
function initDonutChart() {
  const donutCtx = document.getElementById("donutChart");
  if (!donutCtx) return;
  
  // Destruir gráfico existente si hay uno
  if (window.donutChartInstance) {
    window.donutChartInstance.destroy();
  }
  
  // Determinar colores según el modo
  const isLightMode = document.body.classList.contains('light-mode');
  const bgColors = isLightMode 
    ? ["#a78bfa","#c084fc","#e879f9","#7c3aed"]
    : ["#a78bfa","#c084fc","#e879f9","#7c3aed"];
  
  window.donutChartInstance = new Chart(donutCtx, {
    type: "doughnut",
    data: {
      labels: ["Cabello", "Uñas", "Maquillaje", "Spa"],
      datasets: [{
        data: [55640, 11420, 1840, 2120],
        backgroundColor: bgColors,
        borderWidth: 0,
        hoverOffset: 6
      }]
    },
    options: {
      cutout: "72%",
      responsive: true,
      maintainAspectRatio: true,
      plugins: {
        legend: { display: false },
        tooltip: {
          callbacks: {
            label: (ctx) => {
              const v = ctx.parsed;
              return ` ${ctx.label}: $${v.toLocaleString("es-CO")}`;
            }
          }
        }
      }
    }
  });
}

// Línea de beneficio total
function initLineChart() {
  const lineCtx = document.getElementById("lineChart");
  if (!lineCtx) return;
  
  // Destruir gráfico existente si hay uno
  if (window.lineChartInstance) {
    window.lineChartInstance.destroy();
  }
  
  // Determinar colores según el modo
  const isLightMode = document.body.classList.contains('light-mode');
  const borderColor = isLightMode ? "#8b5cf6" : "#8b5cf6";
  const gradientColors = isLightMode 
    ? ["rgba(139,92,246,0.4)", "rgba(139,92,246,0.05)"]
    : ["rgba(139,92,246,0.6)", "rgba(139,92,246,0.05)"];
  
  const gradient = lineCtx.getContext("2d").createLinearGradient(0,0,0,300);
  gradient.addColorStop(0, gradientColors[0]);
  gradient.addColorStop(1, gradientColors[1]);

  const months = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"];
  const values = [56, 49, 62, 55, 78, 81, 95, 110, 97, 87, 104, 120];
  
  window.lineChartInstance = new Chart(lineCtx, {
    type: "line",
    data: {
      labels: months,
      datasets: [{
        label: "Beneficio (miles $)",
        data: values,
        borderColor: borderColor,
        borderWidth: 3,
        pointRadius: 0,
        pointHoverRadius: 6,
        pointBackgroundColor: borderColor,
        fill: true,
        backgroundColor: gradient,
        tension: 0.3
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false }
      },
      scales: {
        y: {
          beginAtZero: true,
          grid: { color: "rgba(255,255,255,0.05)" }
        },
        x: {
          grid: { display: false }
        }
      }
    }
  });
}

// Toggle tema claro/oscuro
function setupThemeToggle() {
  const themeToggle = document.getElementById("themeToggle");
  if (!themeToggle) return;
  
  themeToggle.addEventListener("click", () => {
    document.body.classList.toggle("light-mode");
    
    // Guardar preferencia
    const isLightMode = document.body.classList.contains("light-mode");
    localStorage.setItem("lightMode", isLightMode);
    
    // Recrear gráficos con los nuevos colores
    initDonutChart();
    initLineChart();
    
    // Cambiar ícono según el modo
    themeToggle.textContent = isLightMode ? "🌙" : "☀️";
  });
  
  // Cargar preferencia guardada
  const savedLightMode = localStorage.getItem("lightMode") === "true";
  if (savedLightMode) {
    document.body.classList.add("light-mode");
    themeToggle.textContent = "🌙";
  } else {
    themeToggle.textContent = "☀️";
  }
}

// Toggle sidebar en móviles
function setupSidebarToggle() {
  const sidebar = document.querySelector('.sidebar');
  const sidebarToggle = document.getElementById('sidebarToggle');
  const mobileMenuBtn = document.getElementById('mobileMenuBtn');
  const sidebarOverlay = document.getElementById('sidebarOverlay');
  
  if (mobileMenuBtn && sidebarOverlay) {
    mobileMenuBtn.addEventListener('click', () => {
      sidebar.classList.add('active');
      sidebarOverlay.classList.add('active');
      document.body.style.overflow = 'hidden';
    });
    
    sidebarOverlay.addEventListener('click', () => {
      sidebar.classList.remove('active');
      sidebarOverlay.classList.remove('active');
      document.body.style.overflow = '';
    });
  }
  
  if (sidebarToggle) {
    sidebarToggle.addEventListener('click', () => {
      sidebar.classList.toggle('active');
      if (sidebar.classList.contains('active')) {
        sidebarOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';
      } else {
        sidebarOverlay.classList.remove('active');
        document.body.style.overflow = '';
      }
    });
  }
}

// Inicializar todo cuando el DOM esté listo
document.addEventListener("DOMContentLoaded", () => {
  renderClientsTable();
  initDonutChart();
  initLineChart();
  setupThemeToggle();
  setupSidebarToggle();
  
  // Ajustar gráficos al redimensionar
  window.addEventListener("resize", () => {
    initDonutChart();
    initLineChart();
  });
});