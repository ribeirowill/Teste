package com.pc.cofipa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.cofipa.model.Usuario;
import com.pc.cofipa.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

	public Optional<Usuario> findByEmail(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);

	public Optional<Usuario> findByEmailOrCodigo(String email, Long codigo);

}
