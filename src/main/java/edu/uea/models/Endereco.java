package edu.uea.models;

import edu.uea.dtos.EnderecoDto;

public class Endereco {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String complemento;

    public Endereco(EnderecoDto enderecoDto) {
        this.cep = enderecoDto.cep();
        this.logradouro = enderecoDto.logradouro();
        this.bairro = enderecoDto.bairro();
        this.localidade = enderecoDto.localidade();
        this.uf = enderecoDto.uf();
        this.complemento = enderecoDto.uf();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "CEP: " + cep + "\n" +
                "Logradouro: " + logradouro + "\n" +
                "Bairro: " + bairro + "\n" +
                "Cidade: " + localidade + "\n" +
                "UF: " + uf + "\n" +
                "Complemento: " + complemento;
    }
}
