//package com.example.organiza.controller;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import com.example.dto.ReceitaInfo;
//import com.example.dto.ReceitaResponseDTO;
//import com.example.organiza.model.Receita;
//import com.example.organiza.model.Usuario;
//import com.example.organiza.repository.ReceitaRepository;
//import com.example.organiza.repository.UsuarioRepository;
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/receita")
//public class ReceitaController {
//
//    @Autowired
//    private ReceitaRepository receitaRepository;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @GetMapping
//    public List<Receita> listar() {
//        return receitaRepository.findAll();
//    }
//
//    @PostMapping("/adicionar")
//    public ResponseEntity<ReceitaResponseDTO> criarReceita(
//        @RequestParam Long idUsuario,
//        @RequestBody @Valid ReceitaResponseDTO request,
//        BindingResult bindingResult
//    ) {
//        if (bindingResult.hasErrors()) {
//            List<String> errors = bindingResult.getAllErrors()
//                .stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.toList());
//            return ResponseEntity.badRequest().body(new ReceitaResponseDTO("400", errors, null));
//        }
//
//        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
//        if (usuarioOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Usuario usuario = usuarioOptional.get();
//
//        Receita novaReceita = new Receita();
//        novaReceita.setData(request.getReceitaInfo().getData());
//        novaReceita.setValor(request.getReceitaInfo().getValor());
//        novaReceita.setCategoria(request.getReceitaInfo().getCategoria());
//        novaReceita.setUsuario(usuario);
//
//        receitaRepository.save(novaReceita);
//
//        ReceitaInfo receitaInfo = new ReceitaInfo(
//            novaReceita.getData(),
//            novaReceita.getValor(),
//            novaReceita.getCategoria()
//        );
//
//        ReceitaResponseDTO response = new ReceitaResponseDTO("201", "Receita adicionada com sucesso", receitaInfo, idUsuario);
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Receita> buscarPeloId(@PathVariable Long id) {
//        Optional<Receita> receita = receitaRepository.findById(id);
//        if (receita.isPresent()) {
//            return ResponseEntity.ok(receita.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Receita> atualizarReceita(@PathVariable Long id, @RequestBody Receita receitaAtualizada) {
//        Optional<Receita> receitaExistente = receitaRepository.findById(id);
//
//        if (!receitaExistente.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Receita receita = receitaExistente.get();
//        receita.setData(receitaAtualizada.getData());
//        receita.setValor(receitaAtualizada.getValor());
//        receita.setCategoria(receitaAtualizada.getCategoria());
//
//        receita = receitaRepository.save(receita);
//
//        return ResponseEntity.ok(receita);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
//        Optional<Receita> receitaExistente = receitaRepository.findById(id);
//
//        if (!receitaExistente.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        receitaRepository.deleteById(id);
//
//        return ResponseEntity.noContent().build();
//    }
//}
