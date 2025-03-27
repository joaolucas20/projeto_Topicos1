// betoneira-api/src/main/java/br/unitins/tp1/model/Betoneira.java
package br.unitins.tp1.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Betoneira extends PanacheEntity {

    public String marca;
    public String modelo;
    public double capacidade;
    @Enumerated(EnumType.STRING)
    public TipoBetoneira tipo;

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    public Fabricante fabricante;

    // Getters e setters (opcional, PanacheEntity já fornece acesso aos campos)
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

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}