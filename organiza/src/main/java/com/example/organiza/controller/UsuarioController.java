package com.example.organiza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.example.dto.UsuarioResponseDTO;
import com.example.organiza.model.Usuario;
import com.example.organiza.repository.UsuarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuario", produces = "application/json")
@Api(tags = "UsuarioController", description = "Operações relacionadas a usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @ApiOperation(value = "Listar todos os usuários")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso", response = Usuario.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Requisição inválida"),
        @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar um usuário")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Usuário cadastrado com sucesso", response = UsuarioResponseDTO.class),
        @ApiResponse(code = 400, message = "Requisição inválida"),
        @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public UsuarioResponseDTO adicionar(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setStatusCode("200");
        if (bindingResult.hasErrors()) {
            response.setStatusCode("400");
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
    @ApiOperation(value = "Atualizar um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Usuário atualizado com sucesso", response = Usuario.class),
        @ApiResponse(code = 400, message = "Requisição inválida"),
        @ApiResponse(code = 404, message = "Usuário não encontrado"),
        @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
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
    @ApiOperation(value = "Excluir um usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Usuário excluído com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado"),
        @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        // ID existe ou não
        java.util.Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (!usuarioExistente.isPresent()) {
            // Se o usuário não existe, retorne um ResponseEntity com um status 404 (Not Found).
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);

        // Exclusão bem sucedida.
        return ResponseEntity.noContent().build();
    }}