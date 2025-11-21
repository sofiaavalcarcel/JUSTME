package com.sena.JustMe.security;

import com.sena.JustMe.model.Rol;
import com.sena.JustMe.model.Usuarios;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final Usuarios usuario;

    public CustomUserDetails(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolNombre = Optional.ofNullable(usuario.getRol())
                .map(Rol::getNombre)
                .map(String::toUpperCase)
                .orElse("CLIENTE");

        return List.of(new SimpleGrantedAuthority("ROLE_" + rolNombre));
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuarios getUsuario() {
        return usuario;
    }
}
