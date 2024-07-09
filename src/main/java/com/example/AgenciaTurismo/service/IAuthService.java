package com.example.AgenciaTurismo.service;

import com.example.AgenciaTurismo.dto.AuthResponseDto;
import com.example.AgenciaTurismo.dto.request.LoginDTO;

public interface IAuthService {
    AuthResponseDto login(LoginDTO userDto);
}
