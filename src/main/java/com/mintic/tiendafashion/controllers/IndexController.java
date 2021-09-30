package com.mintic.tiendafashion.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mintic.tiendafashion.models.Usuario;
import com.mintic.tiendafashion.models.dao.IUsuarioRepository;

@Controller
@RequestMapping("/login")
public class IndexController {
	
	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	@RequestMapping({"/",""})
	public String Index(Model model) {
		model.addAttribute("titulo", "hola Spring");
		model.addAttribute("ErrorInicio", false);
		return "inicio";
	}
	
	@PostMapping("/validar")
	public String ValidarExistenciaUsuario(Model model, @RequestParam String user, @RequestParam String password) {
		Optional<Usuario> databaseResponse = usuarioRepo.findByName(user);
		Usuario usuario;
		if(databaseResponse.isPresent()) {
			usuario = databaseResponse.get();
			
			if(usuario.getPassword().equals(password)){
				return "redirect:/usuarios/listarUsuarios";
			}else {
				model.addAttribute("ErrorInicio", true);
				return "inicio";
			}
		}
		else {
			model.addAttribute("ErrorInicio", true);
			return "inicio";
		}
	}
	
}
