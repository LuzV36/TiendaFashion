package com.mintic.tiendafashion.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mintic.tiendafashion.models.Usuario;
import com.mintic.tiendafashion.models.dao.IUsuarioRepository;

@RequestMapping("/usuarios")
@Controller
public class UsuariosController {
	
	@Autowired
	private IUsuarioRepository usuarioRepo;

	@RequestMapping({"/",""})
	public String Index(Model model) {
		model.addAttribute("titulo", "hola Spring");
		return "Usuarios";
	}
	
	@RequestMapping("/agregarUsuario")
	public String AgregarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            return "UsuariosAgregar";
        }
		
		if(usuario.getNombre() != null) {
        	usuario.setIdTipoDocumento(1);
        	usuarioRepo.save(usuario);
            return "redirect:/usuarios/listarUsuarios";        	
        }else {
        	return "UsuariosAgregar";	
        }

	}
	
	@GetMapping("/eliminar/{id}")
	public String EliminarUsuario( @PathVariable("id") long id, Model model ){
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    usuarioRepo.delete(usuario);
	    return "redirect:/usuarios/listarUsuarios";
	}
	
	@RequestMapping("/actualizarUsuario/{id}")
	public String ActualizarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result, Model model) {
		
	    if (result.hasErrors()) {
	        usuario.setId(id);
	        return "UsuariosActualizar";
	    }
	    
		if(id != 0) {
			usuario.setIdTipoDocumento(1);
			usuarioRepo.save(usuario);
			return "redirect:/usuarios/listarUsuarios";
		}
	    
	    usuarioRepo.save(usuario);
	    return "redirect:/index";

	}
	
	@GetMapping("/verUsuario/{id}")
	public String ActivarActualizacion(@PathVariable("id") long id, Model model) {

		Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID Usuario no existe:" + id)); 
		
		model.addAttribute("usuario", usuario);
	    return "UsuariosActualizar";
	}
	
	@RequestMapping("/listarUsuarios")
	public String ListarUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioRepo.findAll());
		return "UsuariosLista";
	}
	
}
