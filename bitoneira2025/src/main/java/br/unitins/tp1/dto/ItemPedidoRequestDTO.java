package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemPedidoRequestDTO {

    @NotNull(message = "Quantidade cannot be null")
    @Positive(message = "Quantidade must be a positive value")
    private Integer quantidade;

    @NotNull(message = "Preco cannot be null")
    @Positive(message = "Preco must be a positive value")
    private Double preco;

    @NotNull(message = "Pedido ID cannot be null")
    private Long pedidoId;

    @NotNull(message = "Betoneira ID cannot be null")
    private Long betoneiraId;

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

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getBetoneiraId() {
        return betoneiraId;
    }

    public void setBetoneiraId(Long betoneiraId) {
        this.betoneiraId = betoneiraId;
    }
}