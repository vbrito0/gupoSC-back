package com.grupoSC.testePraticoBack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupoSC.testePraticoBack.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String>{

	@Query("Select c From Cliente c Where usuario = :usuario AND senha = :senha")
	Optional<Cliente> findByUsuarioAndSenha(@Param("usuario")String usuario, @Param("senha")String senha);

	List<Cliente> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

	Cliente findByUsuario(String username);

}
