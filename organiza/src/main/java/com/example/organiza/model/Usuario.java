package com.example.organiza.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data 
@Entity
@Table (name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private Long cpf;
	@Column(nullable = false)
	private Long telefone;

	public Long getId() {
		return id;
	}

	
	
	

}
