package com.example.dto;

import java.util.ArrayList;
import com.example.organiza.model.Usuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioResponseDTO extends BasicDTO {
    private Usuario usuario;

    public UsuarioResponseDTO() {
        super.setMensagem(new ArrayList<>());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
