package com.grupoSC.testePraticoBack.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoSC.testePraticoBack.dto.LoginRequest;
import com.grupoSC.testePraticoBack.service.ClienteService;
import com.grupoSC.testePraticoBack.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = clienteService.authenticate(loginRequest.getUsuario(), loginRequest.getSenha());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(loginRequest.getUsuario());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login realizado com sucesso");
        response.put("token", token);
        
        return ResponseEntity.ok(response);
    }
}
