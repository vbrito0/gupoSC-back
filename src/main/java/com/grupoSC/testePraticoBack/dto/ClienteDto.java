package com.grupoSC.testePraticoBack.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.grupoSC.testePraticoBack.model.Cliente;
import com.grupoSC.testePraticoBack.model.Cliente.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

	private String cnpj;
	private String razaoSocial;
	private String usuario;
	private String senha;
	private String status;
	
	public static Cliente toCliente(ClienteDto clienteDto, PasswordEncoder passwordEncoder) {
        Cliente cliente = new Cliente();
        cliente.setCnpj(clienteDto.getCnpj());
        cliente.setRazaoSocial(clienteDto.getRazaoSocial());
        cliente.setUsuario(clienteDto.getUsuario());
        cliente.setSenha(passwordEncoder.encode(clienteDto.getSenha()));
        cliente.setStatus(Status.valueOf(clienteDto.getStatus()));

        System.out.println("Cliente criado: " + cliente);

        return cliente;
    }
}
