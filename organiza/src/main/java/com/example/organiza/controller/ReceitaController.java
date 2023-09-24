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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.organiza.model.Receita;
import com.example.organiza.model.Usuario;
import com.example.organiza.repository.ReceitaRepository;
import com.example.organiza.repository.UsuarioRepository;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    

    @GetMapping
    public List<Receita> listar() {
        return receitaRepository.findAll();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarReceita(
        @RequestParam Long idUsuario,
        @RequestBody Receita request
    ) {
        // Verifique se o usuário existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

       
        Receita novaReceita = new Receita();
        novaReceita.setData(request.getData());
        novaReceita.setValor(request.getValor());
        novaReceita.setCategoria(request.getCategoria());
        novaReceita.setUsuario(usuario);

        receitaRepository.save(novaReceita);

        return ResponseEntity.status(HttpStatus.CREATED).body("Receita criada com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarPeloId(@PathVariable Long id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        if (receita.isPresent()) {
            return ResponseEntity.ok(receita.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable Long id, @RequestBody Receita receitaAtualizada) {
        Optional<Receita> receitaExistente = receitaRepository.findById(id);

        if (!receitaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Receita receita = receitaExistente.get();
        receita.setData(receitaAtualizada.getData());
        receita.setValor(receitaAtualizada.getValor());
        receita.setCategoria(receitaAtualizada.getCategoria());
      

        receita = receitaRepository.save(receita);

        return ResponseEntity.ok(receita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        Optional<Receita> receitaExistente = receitaRepository.findById(id);

        if (!receitaExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        receitaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

