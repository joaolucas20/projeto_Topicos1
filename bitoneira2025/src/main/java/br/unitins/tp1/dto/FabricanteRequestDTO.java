package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects; // Import for equals and hashCode

public class FabricanteRequestDTO {

    @NotBlank(message = "Nome cannot be blank")
    @Size(max = 255, message = "Nome cannot exceed 255 characters")
    private String nome;

    @NotBlank(message = "CNPJ cannot be blank")
    @Size(min = 14, max = 14, message = "CNPJ must be 14 characters")
    private String cnpj;

    // Default Constructor
    public FabricanteRequestDTO() {
    }

    // Parameterized Constructor
    public FabricanteRequestDTO(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    // Getters and Setters
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

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FabricanteRequestDTO that = (FabricanteRequestDTO) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cnpj);
    }
}