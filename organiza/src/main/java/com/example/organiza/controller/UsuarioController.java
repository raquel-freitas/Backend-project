package com.example.organiza.controller;

import jakarta.validation.Valid;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UsuarioResponseDTO;
import com.example.organiza.model.Usuario;
import com.example.organiza.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")

public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@GetMapping	
	public List <Usuario> listar() {
		return  usuarioRepository.findAll();
	}
	

	@PostMapping
	public UsuarioResponseDTO adicionar(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
	    UsuarioResponseDTO response = new UsuarioResponseDTO();
	    response.setStatusCode("200");
	    if (bindingResult.hasErrors()) {
	        response.setStatusCode("199");
	        for (ObjectError obj : bindingResult.getAllErrors()) {
	            response.getMensagem().add(obj.getDefaultMessage());
	        }
	    } else {
	        try {
	            usuario = usuarioRepository.save(usuario);
	            response.setUsuario(usuario);
	            response.getMensagem().add("Usuário cadastrado com sucesso");
	        } catch (DataIntegrityViolationException e) {
	            response.setUsuario(usuario);
	            response.getMensagem().add(e.getLocalizedMessage());
	        }
	    }
	    return response;
	}


	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
	    // Verifica se o usuário existe no banco de dados pelo id
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

	    if (!usuarioExistente.isPresent()) {
	        // Se o usuário não existe, retorne um ResponseEntity com um status 404 (Not Found).
	        return ResponseEntity.notFound().build();
	    }

	    // Se existe, atualiza
	    Usuario usuario = usuarioExistente.get();
	    
	    usuario.setNome(usuarioAtualizado.getNome());
	    usuario.setCpf(usuarioAtualizado.getCpf());
	    usuario.setTelefone(usuarioAtualizado.getTelefone());
	    
	    // Salva no repo
	    usuario = usuarioRepository.save(usuario);
	    
	  
	    return ResponseEntity.ok(usuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
	    // ID existe ou não
	    java.util.Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

	    if (!usuarioExistente.isPresent()) {
	        // Se o usuário não existe, retorne um ResponseEntity com um status 404 (Not Found).
	        return ResponseEntity.notFound().build();
	    }

	    usuarioRepository.deleteById(id);

	    //Exclusão bem sucedida.
	    return ResponseEntity.noContent().build();
	}

	
}