// betoneira-api/src/main/java/br/unitins/tp1/dto/request/FabricanteRequestDTO.java
package br.unitins.tp1.dto;

public class FabricanteRequestDTO {
    public String nome;
    public String cnpj;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}