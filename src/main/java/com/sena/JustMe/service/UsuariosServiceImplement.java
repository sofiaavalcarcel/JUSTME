package com.sena.JustMe.service;

import com.sena.JustMe.model.Rol;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IUsuarioRepository;
import com.sena.JustMe.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImplement implements IUsuariosService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuarios save(Usuarios usuario) {
        // Si el usuario no tiene rol, se asigna CLIENTE por defecto
        if (usuario.getRol() == null) {
            Rol rolCliente = rolRepository.findById(3)
                    .orElseThrow(() -> new RuntimeException("El rol con ID=3 no existe en la BD"));
            usuario.setRol(rolCliente);
        }

        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()
                && !isPasswordEncoded(usuario.getContrasena())) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        return usuarioRepository.save(usuario);
    }


    @Override
    public Optional<Usuarios> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuarios> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    // 🔹 NUEVO MÉTODO: actualizar perfil profesional
    public void actualizarPerfilProfesional(Integer idUsuario, String biografia, 
                                            String disponibilidad, MultipartFile[] portafolioFiles) throws IOException {
        Optional<Usuarios> optionalUsuario = usuarioRepository.findById(idUsuario);

        if (optionalUsuario.isPresent()) {
            Usuarios usuario = optionalUsuario.get();

            usuario.setBiografia(biografia);
            usuario.setDisponibilidad(disponibilidad);

            // Si se suben archivos, los convertimos a Base64 y se guardan en la BD
            if (portafolioFiles != null && portafolioFiles.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (MultipartFile file : portafolioFiles) {
                    if (!file.isEmpty()) {
                        String base64 = Base64.getEncoder().encodeToString(file.getBytes());
                        sb.append(base64).append(",");
                    }
                }
                usuario.setPortafolio(sb.toString());
            }

            // Cambiar estado o rol si deseas marcarlo como profesional
            Rol rolProfesional = rolRepository.findById(2) // Ejemplo: rol 2 = PROFESIONAL
                    .orElseThrow(() -> new RuntimeException("El rol con ID=2 no existe en la BD"));
            usuario.setRol(rolProfesional);

            usuario.setEstado("PROFESIONAL");

            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()
                    && !isPasswordEncoded(usuario.getContrasena())) {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }

            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("No se encontró el usuario con ID: " + idUsuario);
        }
    }

    @Override
    public void changePassword(Integer id, String currentPassword, String newPassword) {
        Optional<Usuarios> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuarios usuario = optionalUsuario.get();

            if (passwordEncoder.matches(currentPassword, usuario.getContrasena())) {
                usuario.setContrasena(passwordEncoder.encode(newPassword));
                usuarioRepository.save(usuario);
            } else {
                throw new RuntimeException("La contraseña actual es incorrecta");
            }
        } else {
            throw new RuntimeException("No se encontró el usuario con ID: " + id);
        }
    }

    private boolean isPasswordEncoded(String rawOrEncoded) {
        return rawOrEncoded.startsWith("$2a$") || rawOrEncoded.startsWith("$2b$") || rawOrEncoded.startsWith("$2y$");
    }
}
