document.addEventListener("DOMContentLoaded", () => {
    const acceptBtn = document.getElementById("acceptTerms");
    const declineBtn = document.getElementById("declineTerms");

    if (acceptBtn) {
        acceptBtn.addEventListener("click", () => {
            // Aceptar → vuelve al index y abre el login modal
            window.location.href = "/?showLogin=true";
        });
    }

    if (declineBtn) {
        declineBtn.addEventListener("click", () => {
            // Cancelar → vuelve al inicio normal
            window.location.href = "/";
        });
    }
});
