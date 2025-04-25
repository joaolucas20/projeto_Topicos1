package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneiraEnum;
import java.util.Objects; // Import for equals and hashCode

public class BetoneiraResponseDTO {

    private Long id;
    private String marca;
    private String modelo;
    private double capacidade;
    private TipoBetoneiraEnum tipo;
    private FabricanteResponseDTO fabricante;

    // Default Constructor
    public BetoneiraResponseDTO() {
    }

    // Parameterized Constructor
    public BetoneiraResponseDTO(Long id, String marca, String modelo, double capacidade, TipoBetoneiraEnum tipo, FabricanteResponseDTO fabricante) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TipoBetoneiraEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoBetoneiraEnum tipo) {
        this.tipo = tipo;
    }

    public FabricanteResponseDTO getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteResponseDTO fabricante) {
        this.fabricante = fabricante;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetoneiraResponseDTO that = (BetoneiraResponseDTO) o;
        return Double.compare(that.capacidade, capacidade) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(marca, that.marca) &&
                Objects.equals(modelo, that.modelo) &&
                tipo == that.tipo &&
                Objects.equals(fabricante, that.fabricante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marca, modelo, capacidade, tipo, fabricante);
    }
}