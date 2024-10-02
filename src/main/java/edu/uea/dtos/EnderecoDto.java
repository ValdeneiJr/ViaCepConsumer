package edu.uea.dtos;

public record EnderecoDto(String cep, String logradouro, String bairro,
                          String localidade, String uf, String complemento, boolean erro) {
}
