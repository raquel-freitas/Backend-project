package com.example.dto;

import java.util.ArrayList;

import com.example.organiza.model.Usuario;

public class UsuarioResponseDTO extends BasicDTO{

//	public class UsuarioResponseDTO extends BasicDTO{
	public Usuario usuario;

	public UsuarioResponseDTO() {
			super.setMensagem(new ArrayList<>());
		}
//	}

}
