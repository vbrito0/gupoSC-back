package com.grupoSC.testePraticoBack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupoSC.testePraticoBack.dto.ClienteDto;
import com.grupoSC.testePraticoBack.model.Cliente;
import com.grupoSC.testePraticoBack.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteContoller {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/criar")
	public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDto clienteDto){
		Cliente novoCliente = clienteService.criarCliente(clienteDto);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
	}
	
	@PutMapping("/editar/{cnpj}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable String cnpj, @RequestBody ClienteDto clienteAtualizado) {
        Cliente clienteEditado = clienteService.editarCliente(cnpj, clienteAtualizado);
		return new ResponseEntity<>(clienteEditado, HttpStatus.ACCEPTED);
    }
	
	@DeleteMapping("/excluir/{cnpj}")
    public ResponseEntity<Void> excluirCliente(@PathVariable String cnpj) throws Exception {
        clienteService.excluirCliente(cnpj);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> listarClientesPorRazaoSocial(@RequestParam String razaoSocial) {
        List<Cliente> clientes = clienteService.listarClientesPorRazaoSocial(razaoSocial);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<Cliente> buscarClientePorCnpj(@PathVariable String cnpj) {
        Cliente cliente = clienteService.buscarClientePorCnpj(cnpj);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
