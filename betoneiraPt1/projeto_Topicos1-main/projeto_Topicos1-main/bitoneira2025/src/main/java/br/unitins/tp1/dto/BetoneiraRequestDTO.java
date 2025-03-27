// betoneira-api/src/main/java/br/unitins/tp1/dto/request/BetoneiraRequestDTO.java
package br.unitins.tp1.dto;

import br.unitins.tp1.model.TipoBetoneira;

public class BetoneiraRequestDTO {
    public String marca;
    public String modelo;
    public double capacidade;
    public TipoBetoneira tipo;
    public Long fabricanteId;

    // Getters e setters
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

    public Long getFabricanteId() {
        return fabricanteId;
    }

    public void setFabricanteId(Long fabricanteId) {
        this.fabricanteId = fabricanteId;
    }
}