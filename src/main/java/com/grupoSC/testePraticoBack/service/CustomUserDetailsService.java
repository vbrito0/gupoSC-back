package com.grupoSC.testePraticoBack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grupoSC.testePraticoBack.model.Cliente;
import com.grupoSC.testePraticoBack.repository.ClienteRepository;
import com.grupoSC.testePraticoBack.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsuario(username);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return new CustomUserDetails(cliente);
    }
}
