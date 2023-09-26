package com.example.organiza.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "receita")
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Getter e Setter para 'data' usando formatação
    public String getData() {
        if (data != null) {
            return data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null;
    }

    public void setData(String dataString) {
        // Converter a String em um objeto LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = LocalDate.parse(dataString, formatter);
        this.data = data;
    }

    public Long getId() {
        return id;
    }
}
