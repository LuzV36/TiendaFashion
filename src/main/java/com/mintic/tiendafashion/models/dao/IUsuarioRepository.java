package com.mintic.tiendafashion.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mintic.tiendafashion.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query("select e from Usuario e where e.nombreUsuario = ?1")
	Optional<Usuario> findByName(String name);
}
