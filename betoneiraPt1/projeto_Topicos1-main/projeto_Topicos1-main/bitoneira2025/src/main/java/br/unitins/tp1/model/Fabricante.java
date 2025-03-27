// betoneira-api/src/main/java/br/unitins/tp1/model/Fabricante.java
package br.unitins.tp1.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Fabricante extends PanacheEntity {

    public String nome;
    public String cnpj;

    // Getters e setters (opcional, PanacheEntity já fornece acesso aos campos)
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