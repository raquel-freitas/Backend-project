package com.example.dto;
import java.util.ArrayList;
import java.util.List;
import com.example.organiza.model.Usuario;

import lombok.Data;

public class UsuarioResponseListDTO extends BasicDTO{
	    private int quantidadeTotal;
	    private List<Usuario> usuarios;

	    public UsuarioResponseListDTO() {
	        super.setMensagem(new ArrayList<>());
	    }

	    public int getQuantidadeTotal() {
	        return quantidadeTotal;
	    }

	    public void setQuantidadeTotal(int quantidadeTotal) {
	        this.quantidadeTotal = quantidadeTotal;
	    }

	    public List<Usuario> getUsuarios() {
	        return usuarios;
	    }

	    public void setUsuarios(List<Usuario> usuarios) {
	        this.usuarios = usuarios;
	    }
	}

