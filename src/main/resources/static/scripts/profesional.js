document.addEventListener("DOMContentLoaded", function () {
    // =======================
    // MENU DESPLEGABLE
    // =======================
    const userProfile = document.getElementById("userProfile");
    const dropdownMenu = document.getElementById("dropdownMenu");
    const logoutBtn = document.getElementById("logoutBtn");

    userProfile.addEventListener("click", function (e) {
        e.stopPropagation();
        this.classList.toggle("active");
        dropdownMenu.style.display = this.classList.contains("active")
            ? "block"
            : "none";
    });

    document.addEventListener("click", function () {
        userProfile.classList.remove("active");
        dropdownMenu.style.display = "none";
    });

    logoutBtn.addEventListener("click", function (e) {
        e.preventDefault();
        if (confirm("¿Estás seguro que deseas cerrar sesión?")) {
            window.location.href = "principal.html";
        }
    });

    // =======================
    // PAGINACIÓN DE TABLA
    // =======================
    const rows = document.querySelectorAll("#servicesBody tr");
    const rowsPerPage = 5;
    let currentPage = 0;

    const totalPages = Math.ceil(rows.length / rowsPerPage);
    const pageInfo = document.getElementById("pageInfo");
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");

    function showPage(page) {
        rows.forEach((row, index) => {
            row.style.display =
                index >= page * rowsPerPage && index < (page + 1) * rowsPerPage
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


// =======================
// PAGINACIÓN DE TABLA CITAS
// =======================
const citasRows = document.querySelectorAll("#citasTableBody tr");
const citasRowsPerPage = 5;
let citasCurrentPage = 0;

const citasTotalPages = Math.ceil(citasRows.length / citasRowsPerPage);
const citasPageInfo = document.getElementById("citasPageInfo");
const citasPrevBtn = document.getElementById("citasPrevBtn");
const citasNextBtn = document.getElementById("citasNextBtn");

function showCitasPage(page) {
    citasRows.forEach((row, index) => {
        row.style.display =
            index >= page * citasRowsPerPage && index < (page + 1) * citasRowsPerPage
                ? ""
                : "none";
    });

    citasPageInfo.textContent = `Página ${page + 1} de ${citasTotalPages}`;
    citasPrevBtn.disabled = page === 0;
    citasNextBtn.disabled = page === citasTotalPages - 1;
}

citasPrevBtn.addEventListener("click", () => {
    if (citasCurrentPage > 0) {
        citasCurrentPage--;
        showCitasPage(citasCurrentPage);
    }
});

citasNextBtn.addEventListener("click", () => {
    if (citasCurrentPage < citasTotalPages - 1) {
        citasCurrentPage++;
        showCitasPage(citasCurrentPage);
    }
});

showCitasPage(citasCurrentPage); // Mostrar la primera página al cargar

