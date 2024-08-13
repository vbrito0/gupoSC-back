package com.grupoSC.testePraticoBack.service;


import static com.grupoSC.testePraticoBack.dto.ClienteDto.toCliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupoSC.testePraticoBack.dto.ClienteDto;
import com.grupoSC.testePraticoBack.model.Cliente;
import com.grupoSC.testePraticoBack.model.Cliente.Status;
import com.grupoSC.testePraticoBack.repository.ClienteRepository;


@Service
public class ClienteService {

	@Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
	
	public Cliente criarCliente(ClienteDto clienteDto) {
		Cliente cliente = toCliente(clienteDto, passwordEncoder);
		return clienteRepository.save(cliente);
	}

	public Cliente editarCliente(String cnpj, ClienteDto clienteAtualizado) {
		return clienteRepository.findById(cnpj)
        .map(cliente -> {
            cliente.setRazaoSocial(clienteAtualizado.getRazaoSocial());
            cliente.setUsuario(clienteAtualizado.getUsuario());
            cliente.setSenha(passwordEncoder.encode(clienteAtualizado.getSenha()));
            cliente.setStatus(Status.valueOf(clienteAtualizado.getStatus().toString()));
            return clienteRepository.save(cliente);
        })
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
	}

	public void excluirCliente(String cnpj) throws Exception {
		Cliente cliente = clienteRepository.findById(cnpj).orElseThrow(() -> new Exception("Cliente não encontrado no Banco!"));
		clienteRepository.deleteById(cliente.getCnpj());
	}

	public List<Cliente> listarTodosClientes() {
	       return clienteRepository.findAll();
	}
	
    public List<Cliente> listarClientesPorRazaoSocial(String razaoSocial) {
        return clienteRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
    }

	public Cliente buscarClientePorCnpj(String cnpj) {
		return clienteRepository.findById(cnpj)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
	}

	public Authentication authenticate(String usuario, String senha) {
		try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, senha));
            return authentication;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Usuário ou senha inválidos", e);
        }
	}
}
