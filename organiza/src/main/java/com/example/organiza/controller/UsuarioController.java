package com.example.organiza.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	@ResponseStatus (HttpStatus.CREATED)
	public Usuario adicionar (@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
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
	    
	    // Retorne um ResponseEntity com um status 200 (OK) e o usuário atualizado.
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