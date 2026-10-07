package com.hospital.paciente.service.impl;

import com.hospital.paciente.dto.LoginRequestDTO;
import com.hospital.paciente.dto.LoginResponseDTO;
import com.hospital.paciente.entity.Usuario;
import com.hospital.paciente.repository.UsuarioRepository;
import com.hospital.paciente.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            return LoginResponseDTO.builder()
                    .mensaje("Inicio de sesion exitoso")
                    .username(usuario.getUsername())
                    .rol(usuario.getRol())
                    .autenticado(true)
                    .build();
        } catch (Exception ex) {
            throw new BadCredentialsException("Credenciales invalidas: usuario o contrasena incorrectos");
        }
    }

    @Override
    public String registrarUsuario(String username, String rawPassword, String rol) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .rol(rol != null ? rol : "ROLE_USER")
                .activo(true)
                .build();

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente con contrasena cifrada en BCrypt";
    }

}
