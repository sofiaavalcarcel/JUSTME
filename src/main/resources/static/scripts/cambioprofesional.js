function confirmarCambioAProfesional() {
    const confirmacion = confirm(
        "ATENCIÓN:\n\nUna vez que cambies a cuenta profesional, NO podrás volver a ser un usuario corriente.\n\n¿Deseas continuar?"
    );
    
    if (confirmacion) {
        // Usuario confirmó - proceder con el cambio a profesional
        // Aquí puedes enviar el formulario:
        // Si usas acción en el form, solo descomenta:
        // document.getElementById('formulario-profesional').submit();

        // Por ahora mostramos un alert de confirmación
        alert("Formulario enviado. ¡Gracias por cambiar a profesional!");63
		2
    } else {
        // Usuario canceló - redirigir a otra vista
        // Cambia "otra-vista.html" por la URL a la que quieres redirigir
        window.location.href = "inicio.html";
        
        // Alternativa: usar location.replace() para no guardar en historial
        // window.location.replace("otra-vista.html");
    }
}