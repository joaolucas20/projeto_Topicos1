// betoneira-api/src/main/java/br/unitins/tp1/dto/response/BetoneiraResponseDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneira;

public class BetoneiraResponseDTO {
    public Long id;
    public String marca;
    public String modelo;
    public double capacidade;
    public TipoBetoneira tipo;
    public FabricanteResponseDTO fabricante;

    // Getters e setters
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

    public TipoBetoneira getTipo() {
        return tipo;
    }

    public void setTipo(TipoBetoneira tipo) {
        this.tipo = tipo;
    }

    public FabricanteResponseDTO getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteResponseDTO fabricante) {
        this.fabricante = fabricante;
    }
}