package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class TipoBetoneiraEntity extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    private TipoBetoneiraEnum tipo;

    public TipoBetoneiraEntity() {
    }

    public TipoBetoneiraEntity(TipoBetoneiraEnum tipo) {
        this.tipo = tipo;
    }

    public TipoBetoneiraEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoBetoneiraEnum tipo) {
        this.tipo = tipo;
    }
}