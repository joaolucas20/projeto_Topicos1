package br.unitins.tp1.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class ItemPedido extends PanacheEntity {

    public Integer quantidade;
    public Double preco;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    public Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "betoneira_id")  // Added: Foreign key to Betoneira
    public Betoneira betoneira;         // Added: Relationship with Betoneira

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Betoneira getBetoneira() {
        return betoneira;
    }

    public void setBetoneira(Betoneira betoneira) {
        this.betoneira = betoneira;
    }
}