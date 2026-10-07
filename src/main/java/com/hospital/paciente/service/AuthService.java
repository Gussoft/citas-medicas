package com.hospital.paciente.service;

import com.hospital.paciente.dto.LoginRequestDTO;
import com.hospital.paciente.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

    String registrarUsuario(String username, String rawPassword, String rol);

}
