// Datos simulados de clientes
let clientes = [
  { id: 1, nombre: "María", apellido: "García", email: "maria.garcia@email.com", estado: "activo", rol: 1 },
  { id: 2, nombre: "Carlos", apellido: "López", email: "carlos.lopez@email.com", estado: "activo", rol: 2 },
  { id: 3, nombre: "Ana", apellido: "Martínez", email: "ana.martinez@email.com", estado: "inactivo", rol: 1 },
  { id: 4, nombre: "Juan", apellido: "Rodríguez", email: "juan.rodriguez@email.com", estado: "activo", rol: 3 },
  { id: 5, nombre: "Laura", apellido: "Hernández", email: "laura.hernandez@email.com", estado: "activo", rol: 2 },
  { id: 6, nombre: "Pedro", apellido: "Gómez", email: "pedro.gomez@email.com", estado: "inactivo", rol: 1 },
  { id: 7, nombre: "Sofía", apellido: "Díaz", email: "sofia.diaz@email.com", estado: "activo", rol: 1 },
  { id: 8, nombre: "Miguel", apellido: "Pérez", email: "miguel.perez@email.com", estado: "activo", rol: 2 },
  { id: 9, nombre: "Elena", apellido: "Sánchez", email: "elena.sanchez@email.com", estado: "inactivo", rol: 1 },
  { id: 10, nombre: "David", apellido: "Romero", email: "david.romero@email.com", estado: "activo", rol: 3 }
];

// Variables de estado
let clientesFiltrados = [...clientes];
let paginaActual = 1;
const clientesPorPagina = 5;
let clienteEditando = null;

// Elementos DOM
const tablaClientes = document.getElementById('tablaClientes');
const clientesBody = document.getElementById('clientesBody');
const totalClientes = document.getElementById('totalClientes');
const clientesActivos = document.getElementById('clientesActivos');
const porcentajeActivos = document.getElementById('porcentajeActivos');
const statTomanServicios = document.getElementById('statTomanServicios');
const statOfrecenServicios = document.getElementById('statOfrecenServicios');
const statAdministradores = document.getElementById('statAdministradores');
const actividadClientes = document.getElementById('actividadClientes');
const filtroEstado = document.getElementById('filtroEstado');
const filtroRol = document.getElementById('filtroRol');
const btnLimpiarFiltros = document.getElementById('btnLimpiarFiltros');
const btnNuevoCliente = document.getElementById('btnNuevoCliente');
const prevPage = document.getElementById('prevPage');
const nextPage = document.getElementById('nextPage');
const paginationInfo = document.getElementById('paginationInfo');
const modalOverlay = document.getElementById('modalOverlay');
const modalTitulo = document.getElementById('modalTitulo');
const clienteForm = document.getElementById('clienteForm');
const clienteId = document.getElementById('clienteId');
const modalClose = document.getElementById('modalClose');
const btnCancelar = document.getElementById('btnCancelar');
const btnGuardar = document.getElementById('btnGuardar');
const searchInput = document.getElementById('search');

// Inicializar la vista de clientes
function initClientes() {
  renderClientesTable();
  updateStats();
  setupEventListeners();
  setupSearch();
}

// Renderizar tabla de clientes
function renderClientesTable() {
  clientesBody.innerHTML = '';
  
  const inicio = (paginaActual - 1) * clientesPorPagina;
  const fin = inicio + clientesPorPagina;
  const clientesPagina = clientesFiltrados.slice(inicio, fin);
  
  if (clientesPagina.length === 0) {
    clientesBody.innerHTML = `
      <tr>
        <td colspan="5" style="text-align: center; padding: 20px; color: var(--muted);">
          No se encontraron clientes
        </td>
      </tr>
    `;
    return;
  }
  
  clientesPagina.forEach(cliente => {
    const tr = document.createElement("tr");
    
    // Determinar clase de estado
    const estadoClass = cliente.estado === 'activo' ? 'badge-activo' : 'badge-inactivo';
    const estadoText = cliente.estado === 'activo' ? 'Activo' : 'Inactivo';
    
    // Determinar texto de rol
    let rolText = '';
    switch(cliente.rol) {
      case 1: rolText = 'Toma servicios'; break;
      case 2: rolText = 'Ofrece servicios'; break;
      case 3: rolText = 'Administrador'; break;
    }
    
    tr.innerHTML = `
      <td><strong>${cliente.nombre} ${cliente.apellido}</strong></td>
      <td>${cliente.email}</td>
      <td><span class="badge ${estadoClass}">${estadoText}</span></td>
      <td><span class="badge-rol">${rolText}</span></td>
      <td class="acciones-tabla">
        <button class="btn-accion btn-editar" data-id="${cliente.id}" title="Editar">✏️</button>
        <button class="btn-accion btn-eliminar" data-id="${cliente.id}" title="Eliminar">🗑️</button>
      </td>
    `;
    clientesBody.appendChild(tr);
  });
  
  updatePagination();
}

// Actualizar estadísticas
function updateStats() {
  const total = clientes.length;
  const activos = clientes.filter(c => c.estado === 'activo').length;
  const porcentaje = total > 0 ? Math.round((activos / total) * 100) : 0;
  
  const tomanServicios = clientes.filter(c => c.rol === 1).length;
  const ofrecenServicios = clientes.filter(c => c.rol === 2).length;
  const administradores = clientes.filter(c => c.rol === 3).length;
  
  totalClientes.textContent = total.toLocaleString("es-CO");
  clientesActivos.textContent = activos.toLocaleString("es-CO");
  porcentajeActivos.textContent = `${porcentaje}%`;
  
  statTomanServicios.textContent = tomanServicios.toLocaleString("es-CO");
  statOfrecenServicios.textContent = ofrecenServicios.toLocaleString("es-CO");
  statAdministradores.textContent = administradores.toLocaleString("es-CO");
  
  // Actualizar actividad reciente
  renderActividadReciente();
}

// Renderizar actividad reciente
function renderActividadReciente() {
  actividadClientes.innerHTML = '';
  
  // Tomar los últimos 4 clientes agregados
  const clientesRecientes = [...clientes]
    .sort((a, b) => b.id - a.id)
    .slice(0, 4);
  
  clientesRecientes.forEach(cliente => {
    const li = document.createElement("li");
    const inicial = cliente.nombre.charAt(0);
    const fecha = new Date().toLocaleDateString('es-ES');
    
    li.innerHTML = `
      <span class="avatar sm">${inicial}</span> 
      ${cliente.nombre} ${cliente.apellido} registrado — ${fecha}
    `;
    actividadClientes.appendChild(li);
  });
}

// Actualizar paginación
function updatePagination() {
  const totalPaginas = Math.ceil(clientesFiltrados.length / clientesPorPagina);
  
  paginationInfo.textContent = `Página ${paginaActual} de ${totalPaginas}`;
  prevPage.disabled = paginaActual === 1;
  nextPage.disabled = paginaActual === totalPaginas || totalPaginas === 0;
}

// Filtrar clientes
function filtrarClientes() {
  const estado = filtroEstado.value;
  const rol = filtroRol.value;
  
  clientesFiltrados = clientes.filter(cliente => {
    let cumpleEstado = true;
    let cumpleRol = true;
    
    if (estado) cumpleEstado = cliente.estado === estado;
    if (rol) cumpleRol = cliente.rol.toString() === rol;
    
    return cumpleEstado && cumpleRol;
  });
  
  paginaActual = 1;
  renderClientesTable();
}

// Configurar event listeners
function setupEventListeners() {
  // Filtros
  filtroEstado.addEventListener('change', filtrarClientes);
  filtroRol.addEventListener('change', filtrarClientes);
  btnLimpiarFiltros.addEventListener('click', () => {
    filtroEstado.value = '';
    filtroRol.value = '';
    clientesFiltrados = [...clientes];
    paginaActual = 1;
    renderClientesTable();
  });
  
  // Paginación
  prevPage.addEventListener('click', () => {
    if (paginaActual > 1) {
      paginaActual--;
      renderClientesTable();
    }
  });
  
  nextPage.addEventListener('click', () => {
    const totalPaginas = Math.ceil(clientesFiltrados.length / clientesPorPagina);
    if (paginaActual < totalPaginas) {
      paginaActual++;
      renderClientesTable();
    }
  });
  
  // Modal
  btnNuevoCliente.addEventListener('click', () => {
    abrirModal();
  });
  
  modalClose.addEventListener('click', cerrarModal);
  btnCancelar.addEventListener('click', cerrarModal);
  
  btnGuardar.addEventListener('click', (e) => {
    e.preventDefault();
    guardarCliente();
  });
  
  // Delegación de eventos para botones de editar y eliminar
  clientesBody.addEventListener('click', (e) => {
    if (e.target.classList.contains('btn-editar')) {
      const id = parseInt(e.target.getAttribute('data-id'));
      editarCliente(id);
    }
    
    if (e.target.classList.contains('btn-eliminar')) {
      const id = parseInt(e.target.getAttribute('data-id'));
      eliminarCliente(id);
    }
  });
}

// Configurar búsqueda
function setupSearch() {
  let timeout = null;
  
  searchInput.addEventListener('input', (e) => {
    clearTimeout(timeout);
    
    timeout = setTimeout(() => {
      const termino = e.target.value.toLowerCase().trim();
      
      if (termino === '') {
        clientesFiltrados = [...clientes];
      } else {
        clientesFiltrados = clientes.filter(cliente => {
          const nombreCompleto = `${cliente.nombre} ${cliente.apellido}`.toLowerCase();
          return nombreCompleto.includes(termino) || cliente.email.toLowerCase().includes(termino);
        });
      }
      
      paginaActual = 1;
      renderClientesTable();
    }, 300);
  });
}

// Abrir modal para nuevo/editar cliente
function abrirModal(cliente = null) {
  clienteEditando = cliente;
  
  if (cliente) {
    modalTitulo.textContent = 'Editar cliente';
    clienteId.value = cliente.id;
    document.getElementById('nombre').value = cliente.nombre;
    document.getElementById('apellido').value = cliente.apellido;
    document.getElementById('email').value = cliente.email;
    document.getElementById('estado').value = cliente.estado;
    document.getElementById('rol').value = cliente.rol;
  } else {
    modalTitulo.textContent = 'Nuevo cliente';
    clienteForm.reset();
    clienteId.value = '';
  }
  
  modalOverlay.classList.add('active');
  document.body.style.overflow = 'hidden';
}

// Cerrar modal
function cerrarModal() {
  modalOverlay.classList.remove('active');
  document.body.style.overflow = '';
  clienteEditando = null;
}

// Guardar cliente (nuevo o editado)
function guardarCliente() {
  const nombre = document.getElementById('nombre').value.trim();
  const apellido = document.getElementById('apellido').value.trim();
  const email = document.getElementById('email').value.trim();
  const estado = document.getElementById('estado').value;
  const rol = parseInt(document.getElementById('rol').value);
  const id = clienteId.value ? parseInt(clienteId.value) : null;
  
  // Validaciones básicas
  if (!nombre || !apellido || !email) {
    alert('Por favor, complete todos los campos obligatorios');
    return;
  }
  
  if (clienteEditando) {
    // Editar cliente existente
    const index = clientes.findIndex(c => c.id === id);
    if (index !== -1) {
      clientes[index] = { id, nombre, apellido, email, estado, rol };
    }
  } else {
    // Nuevo cliente
    const nuevoId = clientes.length > 0 ? Math.max(...clientes.map(c => c.id)) + 1 : 1;
    clientes.push({ id: nuevoId, nombre, apellido, email, estado, rol });
  }
  
  // Actualizar vista
  filtrarClientes();
  updateStats();
  cerrarModal();
}

// Editar cliente
function editarCliente(id) {
  const cliente = clientes.find(c => c.id === id);
  if (cliente) {
    abrirModal(cliente);
  }
}

// Eliminar cliente
function eliminarCliente(id) {
  if (confirm('¿Está seguro de que desea eliminar este cliente?')) {
    clientes = clientes.filter(c => c.id !== id);
    filtrarClientes();
    updateStats();
  }
}

// Inicializar cuando el DOM esté listo
document.addEventListener("DOMContentLoaded", initClientes);
function setupThemeChange() {
  const themeToggle = document.getElementById('themeToggle');
  if (themeToggle) {
    themeToggle.addEventListener('click', () => {
      // Esta función debería estar en script.js
      // Simulamos el cambio de clase en el body
      document.body.classList.toggle('light-mode');
      
      // Guardar preferencia
      const isLightMode = document.body.classList.contains('light-mode');
      localStorage.setItem('lightMode', isLightMode);
      
      // Cambiar ícono según el modo
      themeToggle.textContent = isLightMode ? "🌙" : "☀️";
    });
    
    // Cargar preferencia guardada
    const savedLightMode = localStorage.getItem('lightMode') === 'true';
    if (savedLightMode) {
      document.body.classList.add('light-mode');
      themeToggle.textContent = "🌙";
    } else {
      themeToggle.textContent = "☀️";
    }
  }
}

// Inicializar cuando el DOM esté listo
document.addEventListener("DOMContentLoaded", function() {
  initClientes();
  setupThemeChange(); // Añadir esta línea
});