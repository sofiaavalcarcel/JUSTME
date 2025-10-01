function confirmarCambioAProfesional() {
    const confirmacion = confirm(
        "ATENCIÓN:\n\nUna vez que cambies a cuenta profesional, NO podrás volver a ser un usuario corriente.\n\n¿Deseas continuar?"
    );
    if (confirmacion) {
        // Aquí puedes enviar el formulario:
        // Si usas acción en el form, solo descomenta:
        // document.getElementById('formulario-profesional').submit();

        // Por ahora mostramos un alert de confirmación
        alert("Formulario enviado. ¡Gracias por cambiar a profesional!");
    } else {
        // Usuario canceló
        alert("Cambio cancelado.");
    }
}





