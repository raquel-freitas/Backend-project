package com.example.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ReceitaResponseDTO {
    private String statusCode;
    private List<String> mensagem;
    private ReceitaInfo receitaInfo;

    public ReceitaResponseDTO(String statusCode, List<String> mensagem, ReceitaInfo receitaInfo) {
        this.statusCode = statusCode;
        this.mensagem = mensagem;
        this.receitaInfo = receitaInfo;
    }
}

@Data
public class ReceitaInfo {
    private String data;
    private BigDecimal valor;
    private String categoria;

    public ReceitaInfo(String data, BigDecimal valor, String categoria) {
        this.data = data;
        this.valor = valor;
        this.categoria = categoria;
    }
}
