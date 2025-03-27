// betoneira-api/src/main/java/br/unitins/tp1/dto/response/FabricanteResponseDTO.java
package br.unitins.tp1.dto;

public class FabricanteResponseDTO {
    public Long id;
    public String nome;
    public String cnpj;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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