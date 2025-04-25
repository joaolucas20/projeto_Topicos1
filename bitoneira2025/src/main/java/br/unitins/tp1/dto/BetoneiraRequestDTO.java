package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneiraEnum; // Changed import
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Objects; // Import for equals and hashCode

public class BetoneiraRequestDTO {

    @NotBlank(message = "Marca cannot be blank")
    @Size(max = 255, message = "Marca cannot exceed 255 characters")
    private String marca;

    @NotBlank(message = "Modelo cannot be blank")
    @Size(max = 255, message = "Modelo cannot exceed 255 characters")
    private String modelo;

    @NotNull(message = "Capacidade is required")
    @Positive(message = "Capacidade must be a positive value")
    private double capacidade;

    @NotNull(message = "Tipo cannot be null")
    private TipoBetoneiraEnum tipo; // Changed type

    private Long fabricanteId;

    // Default Constructor
    public BetoneiraRequestDTO() {
    }

    // Parameterized Constructor
    public BetoneiraRequestDTO(String marca, String modelo, double capacidade, TipoBetoneiraEnum tipo, Long fabricanteId) { // Changed type
        this.marca = marca;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.fabricanteId = fabricanteId;
    }

    // Getters and Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public TipoBetoneiraEnum getTipo() { // Changed type
        return tipo;
    }

    public void setTipo(TipoBetoneiraEnum tipo) { // Changed type
        this.tipo = tipo;
    }

    public Long getFabricanteId() {
        return fabricanteId;
    }

    public void setFabricanteId(Long fabricanteId) {
        this.fabricanteId = fabricanteId;
    }

    // equals() and hashCode() - Important for comparing objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetoneiraRequestDTO that = (BetoneiraRequestDTO) o;
        return Double.compare(that.capacidade, capacidade) == 0 &&
                Objects.equals(marca, that.marca) &&
                Objects.equals(modelo, that.modelo) &&
                tipo == that.tipo &&
                Objects.equals(fabricanteId, that.fabricanteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marca, modelo, capacidade, tipo, fabricanteId);
    }
}