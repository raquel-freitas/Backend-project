package com.example.organiza.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "O nome não pode estar nulo ou em branco")
    @Length(min = 3, max = 80, message = "O Nome deve conter no máximo 80 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome só deve conter letras")
    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @NotBlank(message = "O CPF não pode estar nulo ou em branco")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos")
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "O telefone não pode estar nulo ou em branco")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter entre 10 e 11 dígitos")
    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}

